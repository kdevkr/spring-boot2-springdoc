package com.example.demo.docs;

import com.example.demo.error.ErrorResponse;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.*;
import java.util.List;
import java.util.stream.Collectors;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocsConfig {
    public static final String DEFAULT_AUTH = "JWT Authentication";
    public static final String OAUTH2 = "OAuth2";

    public Info info() {
        return new Info().title("Open API").description("Api Documentation").version("1.0.0");
    }

    @Bean
    public OpenAPI openAPI(SpringdocServersProperties serversProperties) {
        return new OpenAPI().info(info()).servers(serversProperties.getServers());
    }

    @Bean
    public GroupedOpenApi apiV1(BearerTokenProperties bearerTokenProperties) {
        SecurityScheme bearerScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .in(SecurityScheme.In.HEADER)
            .scheme("bearer")
            .bearerFormat("JWT");

        if (bearerTokenProperties.isEnabled() && !bearerTokenProperties.getTokens().isEmpty()) {
            List<BearerTokenProperties.BearerToken> tokens = bearerTokenProperties.getTokens();
            // NOTE: It is rendered as a markdown.
            String description = tokens.stream()
                .map(item -> String.format("**%s** %s", item.getName(), item.getToken()))
                .collect(Collectors.joining("\n\n"));
            bearerScheme.description(description);
        }

        return GroupedOpenApi.builder()
            .group("v1")
            .pathsToMatch("/api/**")
            .addOpenApiCustomiser(openapi -> openapi.info(info())
                .addSecurityItem(new SecurityRequirement().addList(DEFAULT_AUTH))
                // .components(new Components().addSecuritySchemes(DEFAULT_AUTH, bearerScheme)) // error!
                .getComponents().addSecuritySchemes(DEFAULT_AUTH, bearerScheme)
            )
            .build();
    }

    @Bean
    public GroupedOpenApi oauth2V1() {
        return GroupedOpenApi.builder()
            .group("oauth2_v1")
            .pathsToMatch("/oauth2/**")
            .addOpenApiCustomiser(openapi -> openapi.info(info())
                .addSecurityItem(new SecurityRequirement().addList(OAUTH2))
                .getComponents().addSecuritySchemes(OAUTH2, new SecurityScheme()
                    .type(SecurityScheme.Type.OAUTH2)
                    .description("Client Credentials Flow")
                    .in(SecurityScheme.In.HEADER)
                    .flows(new OAuthFlows()
                        .clientCredentials(
                            new OAuthFlow().tokenUrl("/oauth/token")
                                .scopes(new Scopes()
                                    .addString("all", "all permission"))))
                    .scheme("oauth")))
            .build();
    }

    @Bean
    public GlobalOpenApiCustomizer globalOpenApiCustomizer() {
        return openapi -> {
            openapi.getComponents()
                .addSchemas("ErrorResponse", ModelConverters.getInstance().readAllAsResolvedSchema(ErrorResponse.class).schema);

            openapi.getPaths().values()
                .forEach(pathItem -> pathItem.readOperations()
                    .forEach(operation -> operation.getResponses()
                        .addApiResponse("500", new ApiResponse().description("Server Error")
                            .content(new Content().addMediaType("application/json", new MediaType().schema(new Schema<ErrorResponse>().$ref("ErrorResponse")))))));
        };
    }
}

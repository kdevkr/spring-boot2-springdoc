package com.example.demo.docs;

import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "springdoc")
public class SpringdocServersProperties {
    private final List<Server> servers;
}

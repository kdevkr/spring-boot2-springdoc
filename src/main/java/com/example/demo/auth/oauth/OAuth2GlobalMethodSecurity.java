package com.example.demo.auth.oauth;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // NOTE: Don't use @EnableGlobalMethodSecurity with @EnableTransactionManagement
@Configuration
public class OAuth2GlobalMethodSecurity extends GlobalMethodSecurityConfiguration {

    private final RoleHierarchy roleHierarchy;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        OAuth2MethodSecurityExpressionHandler expressionHandler = new OAuth2MethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }

}

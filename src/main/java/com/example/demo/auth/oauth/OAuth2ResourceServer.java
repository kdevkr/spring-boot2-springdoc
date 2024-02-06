package com.example.demo.auth.oauth;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.TokenStore;

@AllArgsConstructor
@EnableResourceServer
@Configuration
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

    private final TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore)
            .authenticationEntryPoint(new OAuth2AuthenticationEntryPoint())
            .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
            .antMatchers("/oauth2/**")
            .and()
            .authorizeRequests()
            .antMatchers("/oauth2/v1/users/**").access("#oauth2.hasAnyScope('all', 'user')")
            .antMatchers("/oauth2/v1/**").access("#oauth2.isOAuth()")
            .anyRequest().authenticated();
    }

}

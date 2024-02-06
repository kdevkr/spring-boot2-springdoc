package com.example.demo.docs;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("springdoc.bearer")
public class BearerTokenProperties {
    private final boolean enabled;
    private final List<BearerToken> tokens;

    @Getter
    @RequiredArgsConstructor
    public static class BearerToken {
        private final String name;
        private final String token;
    }
}
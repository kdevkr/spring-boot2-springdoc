package com.example.demo.auth.jwt;

import com.example.demo.user.User;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.util.CookieGenerator;

@Slf4j
public class JwtFormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Instant now = Instant.now();
        User principal = (User) authentication.getPrincipal();

        String jwt = JwtUtil.builder()
            .subject(principal.getId())
            .claim("name", authentication.getName())
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(30, ChronoUnit.DAYS)))
            .compact();

        CookieGenerator cookieGenerator = new CookieGenerator();
        cookieGenerator.setCookieName(HttpHeaders.AUTHORIZATION);
        cookieGenerator.setCookiePath("/");
        cookieGenerator.setCookieSecure(true);
        cookieGenerator.setCookieHttpOnly(true);
        cookieGenerator.setCookieMaxAge((int) Duration.ofHours(1).toSeconds()); // seconds
        cookieGenerator.addCookie(response, jwt);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}

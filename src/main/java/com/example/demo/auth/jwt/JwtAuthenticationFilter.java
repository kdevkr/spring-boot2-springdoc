package com.example.demo.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJws;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/oauth")) { // NOTE: Exclude oauth endpoint url.
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            token = authorization.substring(AUTHORIZATION_HEADER_PREFIX.length());
        }

        Cookie cookie = WebUtils.getCookie(request, HttpHeaders.AUTHORIZATION);
        if (token == null && cookie != null) {
            token = cookie.getValue();
        }

        if (StringUtils.hasText(token)) {
            DefaultJws<Claims> jws = JwtUtil.parse(token);
            Claims claims = jws.getPayload();

            List<GrantedAuthority> authorities = new ArrayList<>();

            if ("system".equals(claims.getSubject())) {
                authorities.add(new SimpleGrantedAuthority("ROLE_SYSTEM"));
            } else {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }

            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}

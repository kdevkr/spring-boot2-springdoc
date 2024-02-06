package com.example.demo.auth;

import com.example.demo.user.UserClient;
import com.example.demo.user.UserService;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("auth")
public class AuthPermissionEvaluator implements PermissionEvaluator {
    private final UserService userService;

    public boolean isSystem() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails = userService.loadClientByClientId(authentication.getName());
        if (clientDetails instanceof UserClient) {
            UserClient userClient = (UserClient) clientDetails;
            return "system".equals(userClient.getUser().getId()) && userClient.getScope().contains("all");
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return true;
    }
}

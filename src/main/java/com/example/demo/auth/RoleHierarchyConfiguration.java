package com.example.demo.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;
import org.springframework.security.access.vote.RoleHierarchyVoter;

@Order(0)
@Configuration
public class RoleHierarchyConfiguration {
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        Map<String, List<String>> roleHierarchyMap = new HashMap<>();
        roleHierarchyMap.put("ROLE_SYSTEM", List.of("ROLE_ADMIN"));
        roleHierarchyMap.put("ROLE_ADMIN", List.of("ROLE_USER"));
        String hierarchy = RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap);
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public RoleHierarchyVoter roleHierarchyVoter() {
        RoleHierarchy roleHierarchy = roleHierarchy();
        return new RoleHierarchyVoter(roleHierarchy);
    }
}

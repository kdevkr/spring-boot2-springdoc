package com.example.demo.user;

import java.util.*;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

@Accessors(chain = true)
@Data
public class UserClient implements ClientDetails {

    private String name;
    private String clientId;
    private String clientSecret;
    private String authorities;
    private String scope;
    private String authorizedGrantTypes;
    private String resourceIds;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
    private String autoapprove;
    private String additionalInformation;
    private User user;

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        if (this.resourceIds == null) {
            return Collections.emptySet();
        }
        return Set.of(this.resourceIds.split(","));
    }

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isScoped() {
        return this.scope != null;
    }

    @Override
    public Set<String> getScope() {
        return Set.of(this.scope.split(","));
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        if (this.authorizedGrantTypes == null) {
            return Collections.emptySet();
        }
        return Set.of(this.authorizedGrantTypes.split(","));
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return Collections.emptySet();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if (this.authorities == null) {
            return Collections.emptySet();
        }

        return Arrays.stream(authorities.split(","))
            .map(authority -> new SimpleGrantedAuthority("ROLE_" + authority))
            .collect(Collectors.toSet());
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.accessTokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return this.refreshTokenValidity;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return this.autoapprove != null;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return new HashMap<>();
    }
}

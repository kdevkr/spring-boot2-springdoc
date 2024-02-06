package com.example.demo.auth.oauth;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.transaction.annotation.Transactional;

public class OAuth2TokenService extends DefaultTokenServices {

    @Transactional
    @Override
    public synchronized OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        try {
            return super.createAccessToken(authentication);
        } catch (DuplicateKeyException e) {
            return super.getAccessToken(authentication);
        }
    }

}

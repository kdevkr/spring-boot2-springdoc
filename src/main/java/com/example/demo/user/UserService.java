package com.example.demo.user;

import com.example.demo.db.entity.OAuthClientDetailsEntity;
import com.example.demo.db.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class UserService implements UserDetailsService, ClientDetailsService {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final UserClientRepository userClientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            return objectMapper.convertValue(userEntity, User.class);
        }
        throw new UsernameNotFoundException("Not found user of " + id);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Optional<OAuthClientDetailsEntity> userClient = userClientRepository.findByClientId(clientId);
        if (userClient.isPresent()) {
            UserClient clientDetails = objectMapper.convertValue(userClient, UserClient.class);
            return clientDetails;
        }
        throw new ClientRegistrationException("Not found client of " + clientId);
    }

    @PostConstruct
    public void init() {
        // NOTE: Default Users
        String id = "system";
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            UserEntity newUser = UserEntity.builder()
                .id(id)
                .name("system")
                .password(passwordEncoder.encode(id))
                .email("no-reply@system")
                .build();
            UserEntity createdUser = userRepository.save(newUser);
            if (createdUser.getId() != null) {
                OAuthClientDetailsEntity oAuthClientDetails = OAuthClientDetailsEntity.builder()
                    .clientId(UUID.randomUUID().toString())
                    .clientSecret(UUID.randomUUID().toString())
                    .accessTokenValidity(null)
                    .scope("all,user")
                    .authorizedGrantTypes("client_credentials")
                    .user(createdUser)
                    .build();
                OAuthClientDetailsEntity savedOAuthClientDetails = userClientRepository.save(oAuthClientDetails);

                System.err.println("===== NOTICE: Don't logging in production =====");
                System.err.println(String.format("clientId: %s", savedOAuthClientDetails.getClientId()));
                System.err.println(String.format("clientSecret: %s", savedOAuthClientDetails.getClientSecret()));
                System.err.println("===============================================");
            }
        }
    }

    public User getUser(Long uid) {
        return objectMapper.convertValue(userRepository.findById(uid).orElse(null), User.class);
    }

    public User getUser(String id) {
        return objectMapper.convertValue(userRepository.findById(id).orElse(null), User.class);
    }

    public User getClientUser(String clientId) {
        Optional<OAuthClientDetailsEntity> userClient = userClientRepository.findByClientId(clientId);
        return userClient.map(client -> objectMapper.convertValue(client.getUser(), User.class)).orElse(null);
    }

    public User update(User user) {
        UserEntity updatedUser = userRepository.save(objectMapper.convertValue(user, UserEntity.class));
        if (updatedUser.getUid() > 0) {
            return getUser(user.getId());
        }
        return user;
    }
}

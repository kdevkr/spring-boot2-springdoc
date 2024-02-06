package com.example.demo.user;

import com.example.demo.db.entity.OAuthClientDetailsEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserClientRepository extends CrudRepository<OAuthClientDetailsEntity, String> {
    Optional<OAuthClientDetailsEntity> findByClientId(String clientId);
}

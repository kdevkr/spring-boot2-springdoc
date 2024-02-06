package com.example.demo.user;

import com.example.demo.db.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findById(String id);
    Optional<UserEntity> findByUid(Long uid);
}

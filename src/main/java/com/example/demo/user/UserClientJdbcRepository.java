package com.example.demo.user;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class UserClientJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public Optional<UserClient> findByClientId(String clientId) {
        String sql = "select * from oauth_client_details where client_id = ?";
        List<UserClient> result = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(UserClient.class), clientId);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return result.stream().findFirst();
    }

}

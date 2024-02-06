package com.example.demo.user;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public Optional<User> findById(String id) {
        String sql = "select * from users where id = ?";
        List<User> result = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class), id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return result.stream().findFirst();
    }

    @Transactional
    public boolean create(User user) {
        Number result = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("users")
            .usingGeneratedKeyColumns("uid")
            .usingColumns("id", "password", "name", "email")
            .executeAndReturnKey(new BeanPropertySqlParameterSource(user));
        return result.longValue() > 0;
    }

    @Transactional
    public boolean update(User oldUser) {
        int result = jdbcTemplate.update("UPDATE users SET name = ? WHERE id = ?", oldUser.getName(), oldUser.getId());
        return result > 0;
    }
}

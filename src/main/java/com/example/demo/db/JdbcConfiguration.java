package com.example.demo.db;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@AllArgsConstructor
@EnableTransactionManagement(order = 0)
@EnableJpaAuditing
@Configuration
public class JdbcConfiguration implements TransactionManagementConfigurer {

    private final PlatformTransactionManager transactionManager;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager;
    }
}

package com.near.friendly.config;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 */
@Configuration
@EnableJpaRepositories("com.near.friendly.repository")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DataBaseConfiguration {

    /**
     * Activate the integration of jackson with hibernate
     *
     * @return hibernate version 4 jackson module.
     */
    @Bean
    public Hibernate4Module hibernate4Module() {
        return new Hibernate4Module();
    }
}

package ru.practicum.statservice.source;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"ru.practicum.statservice.repository"})
public class DbConfig {
}

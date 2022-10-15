package ru.practicum.service.source;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"ru.practicum.service.repositories"})
public class DbConfig {
}

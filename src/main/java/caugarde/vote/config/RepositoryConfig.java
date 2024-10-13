package caugarde.vote.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "caugarde.vote.repository.jpa")  // JPA 레포지토리 경로 지정
@EnableRedisRepositories(basePackages = "caugarde.vote.repository.redis")  // Redis 레포지토리 경로 지정
public class RepositoryConfig {
}


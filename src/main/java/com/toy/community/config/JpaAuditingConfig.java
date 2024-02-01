package com.toy.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing//JPA 자동 감사기능 활성화
public class JpaAuditingConfig {
}

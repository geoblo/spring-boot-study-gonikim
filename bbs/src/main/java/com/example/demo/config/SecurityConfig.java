package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class SecurityConfig {

    // 무시해야 할 패턴 설정
    // 시큐리티 필터 체인을 거치지 않도록 설정
    // 최신 스프링에서는 이러한 방식 보다는 시큐리티 필터 체인에서 permitAll 하는 방식을 권장
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/h2-console/**",
                "/css/**",
                "/js/**",
                "/image/**"
        );
    }

}

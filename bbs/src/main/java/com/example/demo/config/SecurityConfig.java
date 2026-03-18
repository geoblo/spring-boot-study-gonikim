package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableMethodSecurity // 메소드 보안 활성화 설정
public class SecurityConfig {
    // 시큐리티 필터 체인 구성
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/error/**").permitAll() // 디버깅용
                        .requestMatchers("/css/**", "/js/**", "/image/**").permitAll() // 정적 리소스
                        .requestMatchers("/", "/article/list", "/article/content").permitAll() // 게시판
                        .requestMatchers("/signup").permitAll() // 인증
                        .requestMatchers("/member/**").hasAuthority("ROLE_ADMIN") // 관리자
                        .anyRequest().authenticated() // 그 외
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
    
    // 패스워드 인코더 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 무시해야 할 패턴 설정
    // 시큐리티 필터 체인을 거치지 않도록 설정
    // 최신 스프링에서는 이러한 방식 보다는 시큐리티 필터 체인에서 permitAll 하는 방식을 권장
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/h2-console/**"
        );
    }

}

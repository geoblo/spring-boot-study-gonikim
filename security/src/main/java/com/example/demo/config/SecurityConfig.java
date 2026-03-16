package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

// 스프링 시큐리티 관련 설정을 위한 빈을 정의할 수 있는 클래스
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/home").permitAll() // 모든 사용자에게 허용
                        .requestMatchers("/member/**").hasAuthority("ROLE_ADMIN") // 로그인한 사용자 중에 해당 권한이 있는 사용자만 접근 허용
                        .anyRequest().authenticated() // 그 외의 모든 요청은 인증된 사용자만 접근 허용
                )
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults());

        return http.build();
    }


    // 임시1: 메모리 기반 사용자 인증
//    @Bean
    public UserDetailsService userDetailsServiceInMemory(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("user")
//                .password("{noop}password") // {noop}은 패스워드 인코딩을 하지 않겠다는 의미
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
//                .password("{noop}password") // {noop}은 패스워드 인코딩을 하지 않겠다는 의미
                .password(passwordEncoder.encode("password"))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    // 임시2: JDBC(데이터베이스) 기반 사용자 인증
//    @Bean
    public UserDetailsService userDetailsServiceJdbc(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 스프링 시큐리티에서 무시해야 할 패턴을 등록
    // 정적 리소스 또는 필요에 따라 h2-console과 같은 패턴을 무시하도록 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
//        return new WebSecurityCustomizer() {
//            @Override
//            public void customize(WebSecurity web) {
//                web.ignoring().requestMatchers(
//                        "/h2-console/**",
//                        "/css/**",
//                        "/js/**",
//                        "/image/**");
//            }
//        };

        // WebSecurityCustomizer는 함수형 인터페이스이므로 람다식으로 간단히 표현할 수 있음
        return web -> web.ignoring().requestMatchers(
        "/h2-console/**",
                "/css/**",
                "/js/**",
                "/image/**"
        );
    }

}

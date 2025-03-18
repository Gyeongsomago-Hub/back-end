package com.gbsw.gbswhub.domain.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화 (필요한 경우 활성화)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**", // Swagger, H2 콘솔 등
                                "/api/login/**", "/api/signup" // 로그인, 회원가입 엔드포인트
                        ).permitAll() // 위의 엔드포인트들은 인증 없이 접근 가능
                        .anyRequest().authenticated() // 그 외의 요청은 인증 필요
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCryptPasswordEncoder 사용
    }
}

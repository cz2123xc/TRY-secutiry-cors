package com.now.awesome.api.config;


import com.now.awesome.api.jwt.JwtFilter;
import com.now.awesome.api.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() { // 암호화 설정
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf 방지 off (일단 사용하지 않는 상태)
                .cors().configurationSource(corsConfigurationSource()) // cors 방지
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 생성하지 않는다.(jwt 토큰 사용 할꺼라서 세션 생성 안함)
                .and()
                .formLogin().disable() // 폼 로그인 설정 제거
                .httpBasic().disable() // http basic 방지
//                .addFilter() // jwt 토큰 필터 추가
                .authorizeRequests()
                .antMatchers("/api/logout").authenticated() // 로그 아웃은 로그인 상태에서만 가능 하도록
                .antMatchers("/api/admin/**").hasRole("ADMIN") // 어드민 요청은 관리자 인증 상태에서만 가능 하도록
                .anyRequest().permitAll(); // 나머지 요청은 모두 허용
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() { // cors 설정
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}


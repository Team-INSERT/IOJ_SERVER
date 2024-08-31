package com.insert.ioj.global.config;

import com.insert.ioj.domain.user.domain.type.Authority;
import com.insert.ioj.global.security.jwt.JwtTokenFilter;
import com.insert.ioj.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final String ADMIN = "ADMIN";
    private final JwtTokenProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(HttpBasicConfigurer::disable)
            .formLogin(FormLoginConfigurer::disable)
            .logout(LogoutConfigurer::disable);

        http
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        http.addFilterBefore(new JwtTokenFilter(jwtProvider),
                UsernamePasswordAuthenticationFilter.class);

        http
            .cors(Customizer.withDefaults())
            .authorizeRequests(auth -> auth
                .requestMatchers("/user").authenticated()
                .requestMatchers(HttpMethod.POST, "/problem").hasRole(ADMIN)
                .requestMatchers(HttpMethod.POST, "/contest").hasRole(ADMIN)
                .requestMatchers("/contest/admin").hasRole(ADMIN)
                .anyRequest().permitAll()
            );

        http.exceptionHandling(handler ->
            handler.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        return http.build();
    }
}

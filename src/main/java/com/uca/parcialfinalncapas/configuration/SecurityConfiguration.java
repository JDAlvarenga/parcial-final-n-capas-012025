package com.uca.parcialfinalncapas.configuration;

import com.uca.parcialfinalncapas.security.JwtAuth;
import com.uca.parcialfinalncapas.security.JwtAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private JwtAuthFilter jwtAuthFilter;
    private JwtAuth jwtAuth;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authManager -> {
//                    authManager.anyRequest().authenticated();
                    authManager.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll();
                    authManager.requestMatchers(HttpMethod.GET, "/api/tickets/**").authenticated();
                    authManager.requestMatchers(HttpMethod.POST, "/api/tickets/**").authenticated();
                    authManager.requestMatchers("/api/**").hasRole("TECH");
                });


        http.exceptionHandling(e -> {
            e.authenticationEntryPoint(jwtAuth);
        });

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
    {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}

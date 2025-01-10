package com.accountService.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for Spring Security settings.
 * This class handles security configurations including JWT authentication and authorization rules.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    /**
     * Constructor for SecurityConfig.
     * @param jwtTokenFilter The JWT token filter to be used for authentication
     */
    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    /**
     * Configures the security filter chain for HTTP security.
     * This method:
     * - Disables CSRF protection
     * - Requires authentication for specific endpoints (/account/onboard, /account/dashboard)
     * - Allows public access to all other endpoints
     * - Adds JWT token filter before username/password authentication
     *
     * @param http The HttpSecurity object to be configured
     * @return The built SecurityFilterChain
     * @throws Exception if there's an error configuring security
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/account/onboard", "/account/dashboard").authenticated()
                .anyRequest().permitAll()
            )
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

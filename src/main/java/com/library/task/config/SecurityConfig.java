package com.library.task.config;

import com.library.task.filter.JwtAuthenticationFilter;
import com.library.task.services.impl.CustomUserDetailsService;
import com.library.task.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/api/auth/login").permitAll()

                // Only ADMIN can register new users
                .requestMatchers("/api/auth/signup").hasRole("ADMIN")

                // Book endpoints
                .requestMatchers(HttpMethod.GET, "/api/books/**").hasAnyRole("ADMIN", "LIBRARIAN", "PATRON") // Allow all to view books
                .requestMatchers(HttpMethod.POST, "/api/books/**").hasAnyRole("ADMIN", "LIBRARIAN") // Only ADMIN and LIBRARIAN can add books
                .requestMatchers(HttpMethod.PUT, "/api/books/**").hasAnyRole("ADMIN", "LIBRARIAN") // Only ADMIN and LIBRARIAN can update books
                .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasAnyRole("ADMIN", "LIBRARIAN") // Only ADMIN and LIBRARIAN can delete books

                // Patron endpoints
                .requestMatchers("/api/patrons/**").hasAnyRole("ADMIN", "LIBRARIAN") // Only ADMIN and LIBRARIAN can manage patrons

                // Borrowing endpoints
                .requestMatchers(HttpMethod.GET, "/api/borrow/**").hasAnyRole("ADMIN", "LIBRARIAN", "PATRON") // Allow all to view borrowing records
                .requestMatchers(HttpMethod.POST, "/api/borrow/**").hasAnyRole("ADMIN", "LIBRARIAN", "PATRON") // Allow all to borrow books
                .requestMatchers(HttpMethod.PUT, "/api/borrow/**").hasAnyRole("ADMIN", "LIBRARIAN", "PATRON") // Allow all to return books

                // Any other request must be authenticated
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil,userDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.finalProject.nisha.config;

import com.finalProject.nisha.filter.JwtRequestFilter;
import com.finalProject.nisha.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*  Deze security is niet de enige manier om het te doen.
    In de andere branch van deze github repo staat een ander voorbeeld
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // PasswordEncoderBean. Deze kun je overal in je applicatie injecteren waar nodig.
    // Je kunt dit ook in een aparte configuratie klasse zetten.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
    // Authorizatie met jwt
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                // Wanneer je deze uncomments, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
//                .requestMatchers("/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/products").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/products/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/products/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/products/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/products/image").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/products/download/{id}").hasAnyRole("ADMIN", "USER")

                .requestMatchers(HttpMethod.POST, "/orderlines").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/orderlines").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/orderlines/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/orderlines/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/orderlines/{id}").hasAnyRole("ADMIN", "USER")

                .requestMatchers(HttpMethod.POST, "/orders").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/orders").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/orders/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/orders/{id}").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/orders/{id}").hasAnyRole("ADMIN", "USER")

                .requestMatchers(HttpMethod.POST, "/invoice").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/invoice").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/invoice/{id}").hasAnyRole("ADMIN","USER")
                .requestMatchers(HttpMethod.PUT, "/invoice/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/invoice/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/invoice/{id}/invoice").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/orderlines/{id}/amount").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/orders/{id}/totalAmount").hasAnyRole("ADMIN", "USER")

                // Je mag meerdere paths tegelijk definieren
                //.requestMatchers("/orderline", "/orders").hasAnyRole("ADMIN", "USER")

                .requestMatchers("/authenticated").authenticated()
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
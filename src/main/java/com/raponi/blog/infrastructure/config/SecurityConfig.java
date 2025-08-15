package com.raponi.blog.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.raponi.blog.application.service.AppAccountServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private final AppAccountServiceImpl appAccountServiceImpl;
  private final JWTFilter jwtFilter;

  public SecurityConfig(AppAccountServiceImpl appAccountServiceImpl, JWTFilter jwtFilter) {
    this.appAccountServiceImpl = appAccountServiceImpl;
    this.jwtFilter = jwtFilter;
  }

  public @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(registry -> {
          registry.requestMatchers("/req/**").permitAll();
          registry.anyRequest().authenticated();
        })
        .httpBasic(Customizer.withDefaults())
        .addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  public @Bean PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public UserDetailsService userDetailsService() {
    return appAccountServiceImpl;
  }

  public @Bean AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(appAccountServiceImpl);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  public @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

}

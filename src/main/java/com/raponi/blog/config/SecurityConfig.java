package com.raponi.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.raponi.blog.service.AppAccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private final AppAccountService appAccountService;

  public SecurityConfig(AppAccountService appAccountService) {
    this.appAccountService = appAccountService;
  }

  public @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(registry -> {
          registry.requestMatchers("/req/**", "/css/**", "/js/**").permitAll();
          registry.anyRequest().authenticated();
        })
        .formLogin(httpForm -> {
          httpForm.loginPage("/req/login").permitAll();
          httpForm.defaultSuccessUrl("/index");
        })
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  public @Bean PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public @Bean UserDetailsService userDetailsService() {
    return appAccountService;
  }

  public @Bean AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(appAccountService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

}

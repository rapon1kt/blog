package com.raponi.blog.infrastructure.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.raponi.blog.application.service.AppAccountServiceImpl;
import com.raponi.blog.application.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

  @Autowired
  private JWTService jwtService;
  private ApplicationContext context;

  public JWTFilter(JWTService jwtService, ApplicationContext context) {
    this.jwtService = jwtService;
    this.context = context;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    String token = null;
    String username = null;
    String role = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      token = authHeader.substring(7);
      username = jwtService.extractUsername(token);
      role = jwtService.extractRole(token);
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      String userId = context.getBean(AppAccountServiceImpl.class).getAccountIdByUsername(username);
      if (jwtService.validateToken(token, userId)) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, null,
            authorities);

        authToken.setDetails(username);
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
  }

}

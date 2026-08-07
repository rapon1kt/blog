package com.raponi.blog.infrastructure.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.raponi.blog.infrastructure.security.JWTAdapter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTConfig extends OncePerRequestFilter {

  private final JWTAdapter jwtAdpter;

  public JWTConfig(JWTAdapter jwtAdapter) {
    this.jwtAdpter = jwtAdapter;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");
    final String username;
    final String token;
    final String role;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    token = authHeader.substring(7);
    role = jwtAdpter.extractRole(token);
    username = jwtAdpter.extractUsername(token);

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          username,
          null,
          Collections.singletonList(authority));
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    }

  }

}

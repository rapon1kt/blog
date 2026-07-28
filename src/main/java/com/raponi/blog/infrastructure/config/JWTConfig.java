package com.raponi.blog.infrastructure.config;

import com.raponi.blog.infrastructure.security.JWTService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JWTConfig extends OncePerRequestFilter {

  private JWTService jwtService;
  private HandlerExceptionResolver exceptionResolver;

  public JWTConfig(JWTService jwtService, HandlerExceptionResolver exceptionResolver) {
    this.jwtService = jwtService;
    this.exceptionResolver = exceptionResolver;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String token;
    final String role;
    final String username;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    token = authHeader.substring(7);

    try {
      username = jwtService.extractUsername(token);
      role = jwtService.extractRole(token);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          username,
          null,
          Collections.singletonList(authority)
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    } catch (ExpiredJwtException | MalformedJwtException e) {
      this.exceptionResolver.resolveException(request, response, null, e);
      return;
    } catch (Exception e) {
      exceptionResolver.resolveException(request, response, null, e);
      return;
    }

    filterChain.doFilter(request, response);
  }
}

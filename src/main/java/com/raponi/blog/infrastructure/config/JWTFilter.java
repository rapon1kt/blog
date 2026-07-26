package com.raponi.blog.infrastructure.config;

import com.raponi.blog.application.service.AppAccountServiceImpl;
import com.raponi.blog.application.service.JWTService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JWTFilter extends OncePerRequestFilter {

  private JWTService jwtService;
  private ApplicationContext context;
  private HandlerExceptionResolver exceptionResolver;

  public JWTFilter(
    JWTService jwtService,
    ApplicationContext context,
    @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver
  ) {
    this.jwtService = jwtService;
    this.context = context;
    this.exceptionResolver = exceptionResolver;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    String token = null;
    String username = null;
    String role = null;

    try {
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7);
        username = jwtService.extractUsername(token);
        role = jwtService.extractRole(token);
      }

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        String userId = context.getBean(AppAccountServiceImpl.class).getAccountIdByUsername(username);
        if (jwtService.validateToken(token, userId)) {
          List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);

          authToken.setDetails(username);
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
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

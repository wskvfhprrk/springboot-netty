package com.hejz.dtu.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String jwt = extractJwtFromRequest(request);

        if (StringUtils.hasText(jwt)) {
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtSecret)
                        .parseClaimsJws(jwt)
                        .getBody();

                String username = claims.getSubject();
                List<String> roles = (List<String>) claims.get("roles");

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

package com.pryalkin.Task.filter;

import com.pryalkin.Task.client.SecurityClient;
import com.pryalkin.Task.dto.request.AuthorizationRequestDTO;
import com.pryalkin.Task.dto.response.AuthorizationResponseDTO;
import com.pryalkin.Task.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final SecurityClient securityClient;
    private AuthService authService;
    public static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)) {
//            response.setStatus(HttpStatus.OK.value());
//        } else {
//            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//            if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
//            String username = jwtTokenProvider.getSubject(token);
//            if (jwtTokenProvider.isTokenValid(username, token) && SecurityContextHolder.getContext().getAuthentication() == null) {
//                List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
//                Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } else {
//                SecurityContextHolder.clearContext();
//            }
//        }
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.substring(TOKEN_PREFIX.length());
        AuthorizationRequestDTO requestDTO = new AuthorizationRequestDTO();
        requestDTO.setUserToken(token);
        requestDTO.setServiceToken(authService.getToken());
        AuthorizationResponseDTO responseDTO = securityClient.authorization(requestDTO);

        SecurityContextHolder.clearContext();
    }

}

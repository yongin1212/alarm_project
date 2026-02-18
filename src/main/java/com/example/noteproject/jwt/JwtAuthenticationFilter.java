package com.example.noteproject.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    private static final List<String> PUBLIC_PATHS = List.of(
            "/auth/login",
            "/auth/signup"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (PUBLIC_PATHS.stream().anyMatch(uri::startsWith)) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            unauthorized(response, "Authorization 헤더가 없거나 Bearer 토큰 형식이 아닙니다.");
            return;
        }

        String token = header.substring(7);

        try {
            String username = jwtUtil.getUsername(token);

            if (username == null || username.isBlank()) {
                unauthorized(response, "토큰에서 사용자 정보를 찾을 수 없습니다.");
                return;
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                Authentication authentication = buildAuthentication(username, request, token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            chain.doFilter(request, response);

        } catch (Exception e) {
            unauthorized(response, "토큰 처리 중 오류가 발생했습니다.");
        }
    }

    private Authentication buildAuthentication(String username,
                                               HttpServletRequest request,
                                               String token) {

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(username, null, authorities);

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return auth;
    }

    private void unauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> body = Map.of(
                "success", false,
                "message", message
        );

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}

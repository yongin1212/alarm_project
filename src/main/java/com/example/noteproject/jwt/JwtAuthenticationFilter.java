package com.example.noteproject.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    private static final List<String> PUBLIC_PATHS = List.of(
            "/auth/login",
            "auth/signup"
    );

//    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (PUBLIC_PATHS.stream().anyMatch(uri::startsWith)) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

//        if (header == null || !header.startsWith("Bearer ")) {
//
//        }
//    }

//    private void unauthorized(HttpServletResponse response) throws IOException {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setCharacterEncoding("UTF-8");
//
//        ApiResponse<Void> apiResponse =
//                ApiResponse.error(ErrorResponse.of(TokenError.UNAUTHORIZED));
//
//        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}


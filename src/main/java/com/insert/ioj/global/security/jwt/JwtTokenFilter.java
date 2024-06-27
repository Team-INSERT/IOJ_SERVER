package com.insert.ioj.global.security.jwt;

import com.insert.ioj.global.error.ErrorResponse;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            String token = jwtProvider.resolveToken(request);

            if (token != null) {
                Authentication authentication = jwtProvider.authorization(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (IojException e) {
            handleException(response, e);
        }
    }

    private void handleException(HttpServletResponse response, IojException e) throws IOException {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse res = ErrorResponse.builder()
            .status(errorCode.getStatus())
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .build();

        log.error(res.toString());
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getStatus());
        response.getWriter().write(res.toString());
    }
}

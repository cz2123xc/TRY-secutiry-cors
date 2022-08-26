package com.now.awesome.api.jwt;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = jwtTokenProvider.getJwtFromRequest(request); // get jwt from header
            if (jwt != null && jwtTokenProvider.validateToken(jwt)) { // jwt 유효성 확인

                String userId = JwtTokenProvider.getUserIdFromToken(jwt);
                UserAuthentication authentication = new UserAuthentication(userId, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // 사용자 정보 세팅
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                throw new ServletException("JWT Token is not valid");
            }

            filterChain.doFilter(request, response); // 다음 필터로 전달

        } catch (Exception e) {
            logger.error("Can NOT get JWT from Header", e);
        }
    }




}

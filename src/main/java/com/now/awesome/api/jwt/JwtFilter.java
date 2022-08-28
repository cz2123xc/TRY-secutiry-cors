package com.now.awesome.api.jwt;

import com.now.awesome.api.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {


    private final JwtTokenProvider jwtTokenProvider;

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request); // get jwt from header
        String requestURI = (request).getRequestURI(); // get request uri
        log.debug("jwt: {}, requestURI: {}", jwt, requestURI);

        try {
            if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) { // if jwt is valid
                log.debug("set Authentication to security context for1 '{}', uri: {}", jwt, requestURI);
                Authentication authentication = jwtTokenProvider.getAuthentication(jwt); // get authentication from jwt
                log.debug("authentication: {}", authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication); // set authentication to security context
                log.debug("set Authentication to security context for2 '{}', uri: {}", authentication.getName(), requestURI);
            } else {
                log.debug("no valid JWT token found, uri: {}", requestURI);
            }
            filterChain.doFilter(request, response); // do filter
        } catch (Exception e) {
            logger.error("Can NOT get JWT from Header", e);
            e.printStackTrace();
        }
    }


    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length()); // 토큰 앞 부분 (Bearer) 제거
        }
        return null;
    }


}

package com.now.awesome.api.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;



@Component
public class JwtTokenProvider {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 3600L; // 일반 토큰 3600초 = 1시간
    private static final long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2 ; // 리프레시 토큰 2일
    private static final String secretKey = "awesome";
    private SecretKey key;


    public JwtTokenProvider() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {

        Date now = new Date();

        return Jwts.builder()
                .setSubject((String)authentication.getPrincipal()) // 사용자
                .setIssuedAt(now) // 발행시간
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME)) // 만료시간
                .signWith(key, SignatureAlgorithm.HS256) // 암호화
                .compact(); // 토큰 생성
    }


    // 토큰에서 아이디 추출
    public static String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


    // 토큰 유효성 + 만료일자 확인
    public Claims validateToken(String jwtToken) { // 토큰을 받아서 토큰의 만료시간을 확인한다.
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();
        } catch (Exception e) {
            throw new IllegalStateException("토큰이 만료되었습니다.");
        }
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length()); // 토큰 앞 부분 (Bearer) 제거
        }
        return null;
    }



}

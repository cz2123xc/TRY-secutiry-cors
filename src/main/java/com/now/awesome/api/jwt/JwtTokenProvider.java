package com.now.awesome.api.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Component
@Slf4j
public class JwtTokenProvider {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 3600L; // 일반 토큰 3600초 = 1시간
    private static final long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2 ; // 리프레시 토큰 2일
    private static final String AUTHORITIES_KEY = "auth";
    private static final String secretKey = "awsomeSecretKeyawsomeSecretKeyawsomeSecretKeyawsomeSecretKeyawsomeSecretKey"; // 키가 짧으니 에어를 낸다 길게 변경
    private SecretKey key;


    public JwtTokenProvider() {
        // set secret key
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


    public String createToken(Authentication authentication) {

        Date now = new Date();

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));


        return Jwts.builder()
                .setSubject((String)authentication.getPrincipal()) // 사용자 아이디
                .claim(AUTHORITIES_KEY, authorities) // URL 별로 권한
                .setIssuedAt(now) // 발행시간
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME)) // 만료시간
                .signWith(key, SignatureAlgorithm.HS256) // 암호화
                .compact(); // 토큰 생성
    }


    // 토큰 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) { // 토큰을 받아서 토큰의 만료시간을 확인한다.
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();
            return true;
        } catch (Exception e) {
            log.info("JWT Token is not valid");
            log.info("Jwt ValidateToken Exception Message : {}", e.getMessage());
            return false;
        }
    }

    // make claims valid
    public Claims makeClaims(String jwtToken) {
        if(!validateToken(jwtToken)) {
            throw new IllegalStateException("토큰이 만료되었습니다.");
        }
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();
    }

    public Authentication getAuthentication(String jwtToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(secretKey).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, jwtToken, authorities);
    }






}

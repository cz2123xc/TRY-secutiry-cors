//package com.now.awesome.api.jwt;
//
//import com.now.awesome.api.dto.TokenDto;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.spec.SecretKeySpec;
//import java.security.Key;
//import java.util.Base64;
//
//@Component
//public class TokenProvider {
//
//    private static final String AUTHORITIES_KEY = "auth"; // 인증권한을 저장할 키
//    private static final String String_BEARER_TYPE = "bearer"; // 토큰 형식
//    private static final long ACCESS_TOKEN_EXPIRE_TIME = 3600L; // 일반 토큰 3600초 = 1시간
//    private static final long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2 ; // 리프레시 토큰 2일
//    private static final String ACCESS_TOKEN_NAME = "access_token"; // 일반 토큰 이름
//    private static final String REFRESH_TOKEN_NAME = "refresh_token"; // 리프레시 토큰 이름
//
//
//    private final Key key; // 토큰을 생성할 때 사용할 암호화 키
//
//
//    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
//        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
//        this.key = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
//    }
//
//    // 토큰 생성
//    public TokenDto generateTokenDto(Authentication authentication) {
//        String authorities = authentication.getAuthorities().toString(); // 인증권한을 저장할 키
//        String accessToken = generateAccessToken(authorities); // 토큰 생성
//        String tokenExpiresIn = generateTokenExpiresIn(); // 토큰 유효시간 생성
//        return TokenDto.builder()
//                .grantType(String_BEARER_TYPE)
//                .accessToken(accessToken)
//                .tokenExpiresIn(tokenExpiresIn)
//                .build();
//
//    }
//
//    // 토큰 생성
//    public void generateAccessToken(String authorities) {
//
//    }
//
//    // 토큰 유효시간 생성
//    public String generateTokenExpiresIn() {
//        return String.valueOf(ACCESS_TOKEN_EXPIRE_TIME);
//    }
//
//
//
//
//
//
//
//
//}

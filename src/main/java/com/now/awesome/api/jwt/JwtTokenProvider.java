package com.now.awesome.api.jwt;

import com.now.awesome.api.domain.Member;
import com.now.awesome.api.repository.MemberRepository;
import com.now.awesome.api.service.MemberService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;



@Component
public class JwtTokenProvider {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 3600L; // 일반 토큰 3600초 = 1시간
    private static final long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2 ; // 리프레시 토큰 2일
    private final String secretKey = "awesome";
    private SecretKey key;

    private MemberService memberService;

    public JwtTokenProvider() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String id, String userId) {

        Date now = new Date();

        return Jwts.builder()
                .setSubject(id) // 인덱스
                .claim("userId", userId) // 유저 계정 아이디
                .setIssuedAt(now) // 발행시간
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME)) // 만료시간
                .signWith(key, SignatureAlgorithm.HS256) // 암호화
                .compact(); // 토큰 생성
    }

    public void getAuthentication(String token) { // 토큰을 받아서 인증 정보 반환한다.
        // 유저 가져오기
        Member member = memberService.getMemberById( Long.parseLong(this.getUserIdentifier(token)));
        return new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities());
    }

    public String getUserIdentifier(String token) { // 인증 정보를 받아서 유저 아이디를 반환한다.
        // 유저 아이디 가져오기 (subject)
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getHeaderToken(HttpServletRequest request) { // 헤더에서 토큰을 받아서 토큰을 복호화한다.
        return request.getHeader("Authorization");
    }

    // 토큰 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) { // 토큰을 받아서 토큰의 만료시간을 확인한다.
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);
            return true;
        } catch (Exception e) {
            throw new IllegalStateException("토큰이 만료되었습니다.");
        }
    }


}

package com.now.awesome.api.controller;

import com.now.awesome.api.domain.Member;
import com.now.awesome.api.exception.InvalidRequest;
import com.now.awesome.api.exception.ServerError;
import com.now.awesome.api.jwt.JwtTokenProvider;
import com.now.awesome.api.request.Login;
import com.now.awesome.api.response.JoinResult;
import com.now.awesome.api.response.TokenDataResponse;
import com.now.awesome.api.service.MemberService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입 요청
     */
    @PostMapping("/api/join")
    /*파라미터 받기*/
    public JoinResult saveMember(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        if(id == null){
            throw new ServerError();
        }
        return new JoinResult(id);
    }

    @PostMapping("/api/login")
    public TokenDataResponse loginMember(@RequestBody @Valid Login login) {

        // 1. 로그인 검증
        Member member = memberService.login(login);

        if(member == null) {
            throw new InvalidRequest("userId", "아이디가 존재하지 않습니다.");
        }

        try {
            Authentication authentication = authenticationManager.authenticate( // step 1 UsernamePasswordAuthenticationToken -> authenticate
                    new UsernamePasswordAuthenticationToken(login.getUserId(), login.getPassword()) // principal, credentials
            );

            SecurityContextHolder.getContext().setAuthentication(authentication); // step 2 getContext -> SecurityContextHolder

            String jwt = jwtTokenProvider.createToken(authentication);

            Claims claims = jwtTokenProvider.makeClaims(jwt);

            return TokenDataResponse.builder()
                    .code("200")
                    .message("success")
                    .token(jwt)
                    .subject(claims.getSubject())
                    .issuedTime(claims.getIssuedAt().toString())
                    .expiredTime(claims.getExpiration().toString())
                    .build();

        } catch (Exception e) {
            throw new ServerError("Token", "토큰 발급 실패");
        }
    }


}

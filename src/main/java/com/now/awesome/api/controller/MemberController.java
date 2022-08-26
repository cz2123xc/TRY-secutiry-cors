package com.now.awesome.api.controller;

import com.now.awesome.api.domain.Member;
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
        log.info("회원가입 컨트롤러");
        Long id = memberService.join(member);
        if(id == null){
            throw new ServerError();
        }
        return new JoinResult(id);
    }

    @PostMapping("/api/login")
    public TokenDataResponse loginMember(@RequestBody @Valid Login login) {

        log.info(login.toString());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUserId(), login.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.createToken(authentication);

        Claims claims = jwtTokenProvider.validateToken(jwt);

        return TokenDataResponse.builder()
                .code("200")
                .message("success")
                .token(jwt)
                .subject(claims.getSubject())
                .issuedTime(claims.getIssuedAt().toString())
                .expiredTime(claims.getExpiration().toString())
                .build();
    }

}

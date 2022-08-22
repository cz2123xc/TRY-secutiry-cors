package com.now.awesome.api.controller;

import com.now.awesome.api.domain.Member;
import com.now.awesome.api.exception.ServerError;
import com.now.awesome.api.response.JoinResult;
import com.now.awesome.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 요청
     */
    @PostMapping("/join")
    /*파라미터 받기*/
    public JoinResult saveMember(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        if(id == null){
            throw new ServerError();
        }
        return new JoinResult(id);
    }





}

package com.now.awesome.api.controller;

import com.now.awesome.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 페이지
     */
    @GetMapping("/join")
    /*파라미터 받기*/
    @CrossOrigin(origins = "*")
    public void signUp(@RequestParam String userId, @RequestParam String userPw, @RequestParam String name, @RequestParam String userEmail) {
        log.info("회원가입 페이지");
    }





}

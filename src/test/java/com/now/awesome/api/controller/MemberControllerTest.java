package com.now.awesome.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.now.awesome.api.domain.Member;
import com.now.awesome.api.repository.MemberRepository;
import com.now.awesome.api.request.Login;
import com.now.awesome.api.service.MemberService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 테스트")
    void loginTest() throws Exception {

        // given

        // 1. 회원가입
        Member member = Member.builder()
                .userId("123")
                .password("123")
                .name("123")
                .email("123")
                .build();
        
        memberService.join(member);

        // 2. 로그인 테스트
        Login login = Login.builder()
                .userId("123")
                .password("123")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // mockMvc 이용해 테스트
        mockMvc.perform(post("/api/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // expected


        // when
    }
}
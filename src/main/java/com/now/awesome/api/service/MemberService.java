package com.now.awesome.api.service;

import com.now.awesome.api.domain.Member;
import com.now.awesome.api.repository.MemberRepository;
import com.now.awesome.api.request.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public Long join(Member member) {

        // 회원가입 중복체크
        validationDuplicateMember(member);

        // 패스워드 암호화
        String encodedPassword = passwordEncoder.encode(member.getUserPw());
        member.setUserPw(encodedPassword);
        memberRepository.save(member);
        return member.getId();
    }

    public void login(Login login) {

        List<Member> member = memberRepository.findByUserId(login.getUserId());

        // 아이디 없을 때
        if(member.isEmpty()){
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        }

        // 비밀번호 일치하지 않을 때
        if(!passwordEncoder.matches(login.getUserPw(), member.get(0).getUserPw())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }


    private void validationDuplicateMember(Member member) {
        // 회원가입 중복체크
        List<Member> findMembers = memberRepository.findByUserId(member.getUserId());

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원 입니다.");
        }
    }


}

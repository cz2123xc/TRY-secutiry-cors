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

    public Long join(Member member) {

        log.info("패스워드");

        // 회원가입 중복체크
        validationDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    public void login(Login login) {

        List<Member> member = memberRepository.findByUserId(login.getUserId());

        if(member.isEmpty()){
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
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

package com.now.awesome.api.service;

import com.now.awesome.api.domain.Member;
import com.now.awesome.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member) {
        // 회원가입 중복체크
        validationDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validationDuplicateMember(Member member) {
        // 회원가입 중복체크
        List<Member> findMembers = memberRepository.findByUserId(member.getUserId());

        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원 입니다.");
        }

    }


}

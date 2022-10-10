package com.teamproject.petapet.web.member;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }

    @Override
    public void deleteMember(String memberId) {
        memberRepository.deleteById(memberId);
    }
}

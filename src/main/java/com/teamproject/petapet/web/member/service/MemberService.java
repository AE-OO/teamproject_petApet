package com.teamproject.petapet.web.member.service;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.member.dto.LoginDto;
import com.teamproject.petapet.web.member.dto.MemberDto;
import com.teamproject.petapet.web.member.dto.MemberResponse;
import com.teamproject.petapet.web.member.dto.TokenDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 박채원 22.10.09 작성
 * 장사론 22.10.19 로그인 추가
 */

public interface MemberService {
    List<Member> getMemberList();
    void deleteMember(String memberId);
    void addMemberReport(String memberId);
    void updateMemberStopDate(String memberId);
    int[] getGenderList();
    List<Integer> getAgeList();

    TokenDto login(LoginDto loginDto);
    MemberResponse join(MemberDto memberDto);

    Optional<Member> findOne(String memberId);

    boolean duplicateCheckMemberId(String memberId);
}

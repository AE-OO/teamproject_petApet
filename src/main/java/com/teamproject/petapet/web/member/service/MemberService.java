package com.teamproject.petapet.web.member.service;

import com.teamproject.petapet.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

public interface MemberService {
    List<Member> getMemberList();
    void deleteMember(String memberId);
    void addMemberReport(String memberId);
    void updateMemberStopDate(String memberId);
    int[] getGenderList();
}

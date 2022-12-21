package com.teamproject.petapet.web.member.service;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.member.dto.*;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

/**
 * 박채원 22.10.09 작성
 * 장사론 22.10.19 로그인 추가
 */

public interface MemberService {
    List<MemberDTO> getMemberList();
    List<MemberDTO> getMemberListForAdmin();
    void deleteMember(String memberId);
    void addMemberReport(String memberId);
    void updateMemberStopDate(String memberId);
    int[] getGenderList();
    List<Integer> getAgeList();

    TokenDTO login(MemberRequestDTO.LoginDTO loginDTO);
    void join(MemberRequestDTO.JoinDTO joinDTO);
    Member findOne(String memberId);

    boolean duplicateCheckMemberId(String memberId);
    Map<String, String> validateHandling(BindingResult bindingResult);
    MemberDTO memberInfo(String memberId);

    boolean checkMemberPw(String memberId, String memberPw);

    void updateMemberInfo (String memberId, MemberRequestDTO.UpdateMemberInfo updateMemberInfo);

    int updateMemberPw (String memberId, String memberPw);

    String findMemberId (MemberRequestDTO.FindMemberIdDTO findMemberIdDTO);

    String findMemberPw (MemberRequestDTO.FindMemberPwDTO findMemberPwDTO);


}

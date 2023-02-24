package com.teamproject.petapet.web.member.service;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.member.dto.*;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 박채원 22.10.09 작성
 * 장사론 22.10.19 로그인 추가
 */

public interface MemberService {
    List<MemberDTO> getMemberList();

    void deleteMember(String memberId);

    void addMemberReport(String memberId);

    void updateMemberStopDate(String memberId);

    int[] getGenderList();

    List<Integer> getAgeList();

    TokenDTO login(MemberRequestDTO.LoginDTO loginDTO);

    void join(MemberRequestDTO.JoinDTO joinDTO);

    Member findOne(String memberId);

    boolean duplicateCheckMemberId(String memberId);

    boolean duplicateCheckMemberEmail(String memberEmail);

    boolean duplicateCheckMemberPhoneNum(String memberPhoneNum);

    Map<String, String> validateHandling(BindingResult bindingResult);

    MemberDTO memberInfo(String memberId);

    boolean checkMemberPw(String memberId, String memberPw);

    void updateMemberInfo(String memberId, MemberRequestDTO.UpdateMemberInfo updateMemberInfo);

    int updateMemberPw(String memberId, String memberPw);

    String findMemberId(MemberRequestDTO.FindMemberIdDTO findMemberIdDTO);

    String findMemberPw(MemberRequestDTO.FindMemberPwDTO findMemberPwDTO);

    String findEmail(String memberId);

    String getMemberImg(String memberId);

    void updateMemberImg(String memberId, String memberImg);

    void deleteMemberImg(String memberId);

    CommunityMemberDTO memberProfile(String memberId);

}

package com.teamproject.petapet.web.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.teamproject.petapet.domain.member.Member;
import lombok.*;

import javax.persistence.Column;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * 장사론 22.10.19 작성
 */
@Data
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDTO {

    private String memberId;
    private List<String> memberBirthday;
    private List<String> memberAddress;
    private String memberPhoneNum;
    private String memberName;
    private String memberGender;
    private String memberEmail;
    private String memberImg;

    public static MemberDTO fromEntity(Member member) {
        return MemberDTO.builder()
                .memberId(member.getMemberId())
                .memberGender(member.getMemberGender())
                .memberAddress(Arrays.asList(member.getMemberAddress().split(",")))
                .memberName(member.getMemberName())
                .memberBirthday(Arrays.asList(String.valueOf(member.getMemberBirthday()).split("-")))
                .memberEmail(member.getMemberEmail())
                .memberImg(member.getMemberImg()==null?"0":member.getMemberImg())
                .memberPhoneNum(member.getMemberPhoneNum())
                .build();
    }

}
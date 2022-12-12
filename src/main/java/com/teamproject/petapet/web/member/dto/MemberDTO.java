package com.teamproject.petapet.web.member.dto;

import com.teamproject.petapet.domain.member.Member;
import lombok.*;

import java.util.Arrays;
import java.util.List;


/**
 * 장사론 22.10.19 작성
 */
@Data
@Builder
public class MemberDTO {

    private String memberId;
    private List<String> memberBirthday;
    private List<String> memberAddress;
    private String memberPhoneNum;
    private String memberName;
    private String memberGender;

    public static MemberDTO fromEntity(Member member) {
        return MemberDTO.builder()
                .memberId(member.getMemberId())
                .memberGender(member.getMemberGender())
                .memberAddress(Arrays.asList(member.getMemberAddress().split(",")))
                .memberName(member.getMemberName())
                .memberBirthday(Arrays.asList(String.valueOf(member.getMemberBirthday()).split("-")))
                .memberPhoneNum(member.getMemberPhoneNum())
                .build();
    }

}
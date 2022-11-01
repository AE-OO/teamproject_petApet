package com.teamproject.petapet.web.member.dto;

import com.teamproject.petapet.domain.member.Member;
import lombok.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


/**
 * 장사론 22.10.19 작성
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private String memberId;
    private String memberPw;
    private List<String> memberBirthday;
    private List<String> memberAddress;
    private String memberPhoneNum;
    private String memberName;
    private String memberGender;

    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .memberPw(member.getMemberPw())
                .memberGender(member.getMemberGender())
                .memberAddress(Arrays.asList(member.getMemberAddress().split(",")))
                .memberName(member.getMemberName())
                .memberBirthday(Arrays.asList(String.valueOf(member.getMemberBirthday()).split("-")))
                .memberPhoneNum(member.getMemberPhoneNum())
                .build();
    }

}
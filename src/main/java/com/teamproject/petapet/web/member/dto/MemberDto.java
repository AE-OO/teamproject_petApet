package com.teamproject.petapet.web.member.dto;

import com.teamproject.petapet.domain.member.Member;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

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
    private String memberBirthday;
    private String memberAddress;
    private String memberPhoneNum;
    private String memberName;
    private String memberGender;

    public static MemberDto from(Member member) {
        if(member == null) return null;
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .build();
    }

}
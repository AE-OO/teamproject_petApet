package com.teamproject.petapet.web.member.dto;

import com.teamproject.petapet.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 장사론 22.10.20 작성
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {
    private String memberId;
    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getMemberId());
    }
}

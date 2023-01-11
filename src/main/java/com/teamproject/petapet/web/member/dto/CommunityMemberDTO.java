package com.teamproject.petapet.web.member.dto;

import com.teamproject.petapet.domain.member.Member;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class CommunityMemberDTO {

    private String memberId;
    private List<String> memberBirthday;
    private String memberGender;
    private String memberImg;
    private String memberJoinDate;
    private int communityCount;
    private int commentCount;


    public static CommunityMemberDTO fromEntity(Member member) {
        return CommunityMemberDTO.builder()
                .memberId(member.getMemberId())
                .memberGender(member.getMemberGender())
                .memberJoinDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(member.getMemberJoinDate()))
                .memberBirthday(Arrays.asList(String.valueOf(member.getMemberBirthday()).split("-")))
                .memberImg(member.getMemberImg()==null?"0":member.getMemberImg())
                .communityCount(member.getCommunity().size())
                .commentCount(member.getComment().size())
                .build();
    }



}
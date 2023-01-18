package com.teamproject.petapet.web.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.*;

import javax.persistence.Column;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


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
    private String memberEmail;
    private String memberImg;
    private String memberJoinDate;
    private Long memberReport;

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

    //박채원 22.12.21 작성
    public static MemberDTO fromEntityForMemberListOfAdminPage(Member member){
        return MemberDTO.builder()
                .memberId(member.getMemberId())
                .memberName(member.getMemberName())
                .memberGender(member.getMemberGender())
                .memberJoinDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(member.getMemberJoinDate()))
                .memberReport(member.getMemberReport())
                .build();
    }


}
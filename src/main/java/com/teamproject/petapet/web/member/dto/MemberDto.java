package com.teamproject.petapet.web.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teamproject.petapet.domain.member.Authority;
import com.teamproject.petapet.domain.member.Member;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
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

    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    private String memberId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull

    @Size(min = 3, max = 100)
    private String memberPw;

    private Date memberBirthday;
    private String memberAddress;
    private String memberPhoneNum;
    private String memberName;
    private String memberGender;

    private Set<AuthorityDto> authorityDtoSet;

    public static MemberDto from(Member member) {
        if(member == null) return null;

        return MemberDto.builder()
                .memberId(member.getMemberId())
                .authorityDtoSet(member.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getRole()).build())
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Member toEntity(MemberDto memberDto, PasswordEncoder passwordEncoder){
        Member member = Member.builder()
                .memberId(memberDto.getMemberId())
                .memberPw(passwordEncoder.encode(memberDto.getMemberPw()))
                .memberGender(memberDto.getMemberGender())
                .memberAddress(memberDto.getMemberAddress())
                .memberBirthday(memberDto.getMemberBirthday())
                .memberName(memberDto.getMemberName())
                .memberPhoneNum(memberDto.getMemberPhoneNum())
                .activated(true)
                .build();
        member.addAuthority(Authority.ofUser(member));
        return member;
    }

}
package com.teamproject.petapet.web.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teamproject.petapet.domain.member.Authority;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.validatiion.SmsConfirmNum;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.Set;

/**
 * 장사론 22.10.26 작성 - 회원가입용 Dto, validation 적용
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@MemberPwEquals //커스텀 어노테이션
public class JoinDto {

    @Pattern(regexp = "^[a-z0-9_-]{5,20}$" , message = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String memberId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "^(?=.*[a-zA-z0-9])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$",message = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String memberPw;

    @NotBlank(message = "비밀번호 확인은 필수 입력값입니다.")
    private String memberPw2;

    @NotBlank(message = "생년월일은 필수 입력값입니다.")
    private String memberBirthday;

    @NotBlank(message = "주소는 필수 입력값입니다.")
    private String memberAddress;


    @Pattern(regexp = "^([01]{2})([0|1|6|7|8|9]{1})([0-9]{3,4})([0-9]{4})$", message = "형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요." )
    @NotBlank(message = "인증이 필요합니다.")
    private String memberPhoneNum;


    @Pattern(regexp = "^[가-힣]{2,6}$", message = "한글을 사용하세요. (특수기호, 공백 사용 불가)")
    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String memberName;

    private String memberGender;

    @SmsConfirmNum //커스텀 어노테이션
    @NotBlank(message="")
    private String smsConfirmNum; //문자 인증번호 확인용

    private Set<AuthorityDto> authorityDtoSet;

    public static Member toEntity(JoinDto joinDto, PasswordEncoder passwordEncoder){
        Member member = Member.builder()
                .memberId(joinDto.getMemberId())
                .memberPw(passwordEncoder.encode(joinDto.getMemberPw()))
                .memberGender(joinDto.getMemberGender())
                .memberAddress(joinDto.getMemberAddress())
                .memberBirthday(Date.valueOf(joinDto.getMemberBirthday().replace(",","-")))
                .memberName(joinDto.getMemberName())
                .memberPhoneNum(joinDto.getMemberPhoneNum())
                .activated(true)
                .build();
        member.addAuthority(Authority.ofUser(member));
        return member;
    }

}
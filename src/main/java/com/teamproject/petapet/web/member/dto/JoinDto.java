package com.teamproject.petapet.web.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teamproject.petapet.domain.member.Authority;
import com.teamproject.petapet.domain.member.Member;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

/**
 * 장사론 22.10.26 작성 - 회원가입용 Dto
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinDto {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
//    @Size(min = 5, max = 20)
    @Pattern(regexp = "^[a-z0-9_-]{5,20}$" , message = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
    private String memberId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-z0-9])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$",message = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
//    @Size(min = 8, max = 16)
    private String memberPw;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, max = 16)
    private String memberPw2;

    @NotBlank(message = "생년월일은 필수 입력값입니다.")
    private String memberBirthday;

    @NotBlank(message = "주소는 필수 입력값입니다.")
    private String memberAddress;

    @NotBlank(message = "휴대전화는 필수 입력값입니다.")
    @Pattern(regexp = "^([01]{2})([0|1|6|7|8|9]{1})([0-9]{3,4})([0-9]{4})$", message = "형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요." )
    @Size(min=10, max=11)
    private String memberPhoneNum;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Pattern(regexp = "^[가-힣]{2,6}$", message = "한글을 사용하세요. (특수기호, 공백 사용 불가)")
    @Size(min = 2, max = 6)
    private String memberName;

    private String memberGender;

    @NotBlank(message = "인증이 필요합니다.")
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
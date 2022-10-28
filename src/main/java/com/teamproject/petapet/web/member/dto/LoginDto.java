package com.teamproject.petapet.web.member.dto;

import com.teamproject.petapet.validatiion.MemberIdDuplicate;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 장사론 22.10.19 작성
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @Pattern(regexp = "^[a-z0-9_-]{5,20}$" , message = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @MemberIdDuplicate
    private String memberId;

    @Pattern(regexp = "^(?=.*[a-zA-z0-9])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$",message = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String memberPw;
}
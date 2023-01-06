package com.teamproject.petapet.web.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teamproject.petapet.domain.member.Authority;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.member.validatiion.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.Set;

public class MemberRequestDTO {

    @Data
    @PasswordEquals(text = "memberPw", comparison = "memberPw2") //custom validation
    public static class JoinDTO {

        @Pattern(regexp = "^[a-z0-9_-]{5,20}$" , message = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
        @NotBlank(message = "아이디는 필수 입력값입니다.")
        @NotDuplicateMemberId //custom validation
        private String memberId;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Pattern(regexp = "^(?=.*[a-zA-z0-9])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$",message = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        private String memberPw;

        @NotBlank(message = "비밀번호 확인은 필수 입력값입니다.")
        private String memberPw2;

        @NotBlank(message = "생년월일은 필수 입력값입니다.")
        @Pattern(regexp ="^(19[0-9][0-9]|20\\d{2}),([1-9]|0[1-9]|1[0-2]),(0[1-9]|[1-9]|[1-2][0-9]|3[0-1])$",
                message="생년월일을 다시 확인해주세요.")
        private String memberBirthday;

        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
                message = "형식에 맞게 이메일 주소를 입력해주세요.")
        @NotDuplicateMemberEmail
        private String memberEmail;

//        @NotBlank(message = "주소는 필수 입력값입니다.")
        @AddressNotBlank
        private String memberAddress;

        @Pattern(regexp = "^([01]{2})([0|1|6|7|8|9]{1})([0-9]{3,4})([0-9]{4})$",
                message = "형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요." )
        @NotBlank(message = "인증이 필요합니다.")
        @NotDuplicateMemberPhoneNum
        private String memberPhoneNum;

        @Pattern(regexp = "^[가-힣]{2,6}$", message = "한글을 사용하세요. (특수기호, 공백 사용 불가)")
        @NotBlank(message = "이름은 필수 입력값입니다.")
        private String memberName;

        private String memberGender;

        @SmsConfirmNum //custom validation
        @NotBlank(message="")
        private String smsConfirmNum; //문자 인증번호 확인용

        private Set<AuthorityDTO> authorityDTOSet;

        public Member toEntity(PasswordEncoder passwordEncoder){
            Member member = Member.builder()
                    .memberId(memberId)
                    .memberPw(passwordEncoder.encode(memberPw))
                    .memberGender(memberGender)
                    .memberAddress(memberAddress)
                    .memberBirthday(Date.valueOf(memberBirthday.replace(",","-")))
                    .memberEmail(memberEmail)
                    .memberName(memberName)
                    .memberPhoneNum(memberPhoneNum)
                    .activated(true)
                    .build();
            member.addAuthority(Authority.ofMember(member));
            return member;
        }
    }

    @Data
    public static class LoginDTO {
        @Pattern(regexp = "^[a-z0-9_-]{5,20}$" , message = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
        @NotBlank(message = "아이디는 필수 입력값입니다.")
        @DuplicateMemberId
        private String memberId;

        @Pattern(regexp = "^(?=.*[a-zA-z0-9])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$",message = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        private String memberPw;
    }

    @Data
    @Builder
    @PasswordEquals(text = "newMemberPw", comparison = "newMemberPw2") //custom validation
    public static class UpdateMemberPwDTO {
        @NotBlank
        private String originalMemberPw;
        @Pattern(regexp = "^(?=.*[a-zA-z0-9])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$")
        @NotBlank
        private String newMemberPw;
        @NotBlank
        private String newMemberPw2;
    }

    @Data
    @Builder
    public static class UpdateMemberInfo {
        @NotBlank
        @Pattern(regexp = "^(19[0-9][0-9]|20\\d{2}),([1-9]|0[1-9]|1[0-2]),(0[1-9]|[1-9]|[1-2][0-9]|3[0-1])$")
        private String memberBirthday;
        @AddressNotBlank
        private String memberAddress;
        @Pattern(regexp = "^([01]{2})([0|1|6|7|8|9]{1})([0-9]{3,4})([0-9]{4})$")
        @NotBlank
        private String memberPhoneNum;
        private String memberGender;
        @SmsConfirmNum //custom validation
        private String smsConfirmNum; //문자 인증번호 확인용
//        private MultipartFile memberImg;
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
                message = "형식에 맞게 이메일 주소를 입력해주세요.")
        private String memberEmail;

        public Member toEntity(){
            Member member = Member.builder()
                    .memberGender(memberGender)
                    .memberAddress(memberAddress)
                    .memberBirthday(Date.valueOf(memberBirthday.replace(",","-")))
                    .memberPhoneNum(memberPhoneNum)
                    .memberEmail(memberEmail)
                    .build();
            return member;
        }
    }

    @Data
    @Builder
    public static class FindMemberIdDTO{
        @NotBlank(message = "인증이 필요합니다.")
        private String memberPhoneNum;

        @NotBlank(message = "이름은 필수 입력값입니다.")
        private String memberName;

        @SmsConfirmNum
        @NotBlank(message="인증번호는 필수 입력값입니다.")
        private String smsConfirmNum;
    }

    @Data
    @Builder
    public static class FindMemberPwDTO{

        @NotBlank(message = "아이디는 필수 입력값입니다.")
        @DuplicateMemberId //custom validation
        private String memberId;

        @NotBlank(message = "인증이 필요합니다.")
        private String memberPhoneNum;

        @NotBlank(message = "이름은 필수 입력값입니다.")
        private String memberName;

        @SmsConfirmNum
        @NotBlank(message="인증번호는 필수 입력값입니다.")
        private String smsConfirmNum;
    }

}

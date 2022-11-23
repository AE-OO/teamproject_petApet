package com.teamproject.petapet.web.company.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teamproject.petapet.domain.company.Company;
import com.teamproject.petapet.domain.member.Authority;
import com.teamproject.petapet.web.company.validation.DuplicateCompanyId;
import com.teamproject.petapet.web.company.validation.DuplicateCompanyNumber;
import com.teamproject.petapet.web.company.validation.NotDuplicateCompanyId;
import com.teamproject.petapet.web.company.validation.NotDuplicateCompanyNumber;
import com.teamproject.petapet.web.member.dto.AuthorityDTO;
import com.teamproject.petapet.web.member.validatiion.PasswordEquals;
import com.teamproject.petapet.web.member.validatiion.SmsConfirmNum;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

public  class CompanyRequestDTO {

    @Data
    @PasswordEquals(text = "companyPw", comparison = "companyPw2") //custom validation
    public static class JoinDTO {
        @Pattern(regexp = "^[a-z0-9_-]{5,20}$" , message = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
        @NotBlank(message = "아이디는 필수 입력값입니다.")
        @NotDuplicateCompanyId //custom validation
        private String companyId;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Pattern(regexp = "^(?=.*[a-zA-z0-9])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$",message = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        private String companyPw;

        @NotBlank(message = "비밀번호 확인은 필수 입력값입니다.")
        private String companyPw2;

        @Pattern(regexp = "^([01]{2})([0|1|6|7|8|9]{1})([0-9]{3,4})([0-9]{4})$",
                message = "형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요." )
        @NotBlank(message = "인증이 필요합니다.")
        private String companyPhoneNum;

        @Pattern(regexp = "^([0-9]{3})([0-9]{2})([0-9]{5})$",
                message = "형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요." )
        @NotBlank(message = "사업자번호는 필수 입력값입니다.")
        @NotDuplicateCompanyNumber
        private String companyNumber;

        @Pattern(regexp = "^[가-힣|a-z|A-Z]{1,20}$", message = "한글 또는 영문만 20자까지 입력해주세요.")
        @NotBlank(message = "상호명 필수 입력값입니다.")
        private String companyName;

        @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
                message = "형식에 맞게 이메일 주소를 입력해주세요.")
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        private String companyEmail;

        @SmsConfirmNum //custom validation
        @NotBlank(message="")
        private String smsConfirmNum; //문자 인증번호 확인용

        private Set<AuthorityDTO> authorityDTOSet;

        public Company toEntity(PasswordEncoder passwordEncoder){
            Company company = Company.builder()
                    .companyId("*"+companyId)
                    .companyPw(passwordEncoder.encode(companyPw))
                    .companyName(companyName)
                    .companyNumber(companyNumber)
                    .companyEmail(companyEmail)
                    .companyPhoneNum(companyPhoneNum)
                    .activated(false)
                    .build();
            company.addAuthority(Authority.ofCompany(company));
            return company;
        }
    }

    @Data
    public static class LoginDTO {
        @Pattern(regexp = "^[a-z0-9_-]{5,20}$" , message = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
        @NotBlank(message = "아이디는 필수 입력값입니다.")
        @DuplicateCompanyId
        private String companyId;

        @Pattern(regexp = "^(?=.*[a-zA-z0-9])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$",message = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        private String companyPw;
    }

    @Data
    @Builder
    public static class FindCompanyIdDTO{
        @NotBlank(message = "상호명은 필수 입력값입니다.")
        private String companyName;

        @NotBlank(message = "사업자 번호는 필수 입력값입니다.")
        @DuplicateCompanyNumber
        private String companyNumber;
    }

    @Data
    @Builder
    public static class FindCompanyPwDTO{
        @NotBlank(message = "아이디는 필수 입력값입니다.")
        @DuplicateCompanyId //custom validation
        private String companyId;

        @NotBlank(message = "상호명은 필수 입력값입니다.")
        private String companyName;

        @NotBlank(message = "사업자 번호는 필수 입력값입니다.")
        @DuplicateCompanyNumber
        private String companyNumber;
    }

}

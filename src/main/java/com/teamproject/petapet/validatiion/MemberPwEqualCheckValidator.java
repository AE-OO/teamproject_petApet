package com.teamproject.petapet.validatiion;

import com.teamproject.petapet.web.member.dto.JoinDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * 장사론 22.10.28 작성
 * 비밀번호 확인용
 */
@Component
public class MemberPwEqualCheckValidator extends AbstractValidator<JoinDto> {

    @Override
    protected void doValidate(JoinDto joinDto, Errors errors) {
        if (!joinDto.getMemberPw().equals(joinDto.getMemberPw2())) {
            errors.rejectValue("memberPw2", "비밀번호 일치 오류", "비밀번호가 일치하지 않습니다.2");
        }
    }

}


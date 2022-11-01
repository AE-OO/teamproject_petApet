package com.teamproject.petapet.validatiion;

import com.teamproject.petapet.web.member.dto.JoinDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MemberPwEqualsCheck implements ConstraintValidator<MemberPwEquals, JoinDto> {
    @Override
    public boolean isValid(JoinDto joinDto, ConstraintValidatorContext context) {
        return joinDto.getMemberPw().equals(joinDto.getMemberPw2());
    }
}

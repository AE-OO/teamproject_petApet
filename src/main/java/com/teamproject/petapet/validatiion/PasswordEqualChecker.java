package com.teamproject.petapet.validatiion;

import com.teamproject.petapet.web.member.dto.JoinDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 장사론 22.10.27 작성 - validation 처리 Class
 */
public class PasswordEqualChecker implements ConstraintValidator<PasswordEquals, JoinDto> {
    @Override
    public boolean isValid(JoinDto value, ConstraintValidatorContext context) {
        return value.getMemberPw().equals(value.getMemberPw2());
    }
}

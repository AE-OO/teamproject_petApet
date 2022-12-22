package com.teamproject.petapet.web.member.validatiion;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 장사론 22.10.27 작성
 * 인증문자 번호 확인용
 */
public class AddressNotBlankCheck implements ConstraintValidator<AddressNotBlank, String> {

    @Override
    public boolean isValid(String memberAddress, ConstraintValidatorContext context) {
        return !memberAddress.contains(",,");
    }
}

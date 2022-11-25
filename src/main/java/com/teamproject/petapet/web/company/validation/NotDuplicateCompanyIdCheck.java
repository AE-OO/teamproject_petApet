package com.teamproject.petapet.web.company.validation;

import com.teamproject.petapet.domain.company.CompanyRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 장사론 22.10.28 작성
 * 회원가입용 아이디 중복검사 validation
 * (아이디가 존재하지 않을 경우에만 회원가입 가능)
 */
@RequiredArgsConstructor
public class NotDuplicateCompanyIdCheck implements ConstraintValidator<NotDuplicateCompanyId, String> {

    private final CompanyRepository companyRepository;

    @Override
    public boolean isValid(String companyId, ConstraintValidatorContext context) {
        return !companyRepository.existsById(companyId);
    }
}

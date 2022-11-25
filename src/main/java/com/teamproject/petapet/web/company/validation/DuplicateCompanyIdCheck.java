package com.teamproject.petapet.web.company.validation;

import com.teamproject.petapet.domain.company.CompanyRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 장사론 22.10.28 작성
 * 로그인용 아이디 중복검사 validation
 * (아이디가 존재할 경우에만 로그인 허용)
 */
@RequiredArgsConstructor
public class DuplicateCompanyIdCheck implements ConstraintValidator<DuplicateCompanyId, String> {

    private final CompanyRepository companyRepository;

    @Override
    public boolean isValid(String companyId, ConstraintValidatorContext context) {
        return companyRepository.existsById("*"+companyId);
    }
}

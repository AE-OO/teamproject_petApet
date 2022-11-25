package com.teamproject.petapet.web.company.validation;

import com.teamproject.petapet.domain.company.CompanyRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 장사론 22.10.28 작성
 * 사업자번호 검사 validation
 */
@RequiredArgsConstructor
public class NotDuplicateCompanyNumberCheck implements ConstraintValidator<NotDuplicateCompanyNumber, String> {

    private final CompanyRepository companyRepository;

    @Override
    public boolean isValid(String companyNumber, ConstraintValidatorContext context) {
        return !companyRepository.existsByCompanyNumber(companyNumber);
    }
}

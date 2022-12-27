package com.teamproject.petapet.web.company.validation;

import com.teamproject.petapet.domain.company.CompanyRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class NotDuplicateCompanyPhoneNumCheck implements ConstraintValidator<NotDuplicateCompanyPhoneNum, String> {
    private final CompanyRepository companyRepository;

    @Override
    public boolean isValid(String companyPhoneNum, ConstraintValidatorContext context) {
        return !companyRepository.existsByCompanyPhoneNum(companyPhoneNum);
    }
}

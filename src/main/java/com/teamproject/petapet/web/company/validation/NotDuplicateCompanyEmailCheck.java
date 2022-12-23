package com.teamproject.petapet.web.company.validation;

import com.teamproject.petapet.domain.company.CompanyRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class NotDuplicateCompanyEmailCheck implements ConstraintValidator<NotDuplicateCompanyEmail, String> {
    private final CompanyRepository companyRepository;
    @Override
    public boolean isValid(String companyEmail, ConstraintValidatorContext context) {
        return !companyRepository.existsByCompanyEmail(companyEmail);
    }
}

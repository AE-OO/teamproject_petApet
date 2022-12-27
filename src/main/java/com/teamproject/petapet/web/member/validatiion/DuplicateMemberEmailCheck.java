package com.teamproject.petapet.web.member.validatiion;

import com.teamproject.petapet.domain.company.CompanyRepository;
import com.teamproject.petapet.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class DuplicateMemberEmailCheck implements ConstraintValidator<DuplicateMemberEmail, String> {
    private final MemberRepository memberRepository;
    @Override
    public boolean isValid(String memberEmail, ConstraintValidatorContext context) {
        return memberRepository.existsByMemberEmail(memberEmail);
    }
}
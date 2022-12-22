package com.teamproject.petapet.web.member.validatiion;

import com.teamproject.petapet.domain.company.CompanyRepository;
import com.teamproject.petapet.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class NotDuplicateMemberPhoneNumCheck implements ConstraintValidator<NotDuplicateMemberPhoneNum, String> {
    private final MemberRepository memberRepository;
    @Override
    public boolean isValid(String memberPhoneNum, ConstraintValidatorContext context) {
        return !memberRepository.existsByMemberPhoneNum(memberPhoneNum);
    }
}

package com.teamproject.petapet.validatiion;

import com.teamproject.petapet.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class MemberIdDuplicateCheck implements ConstraintValidator<MemberIdDuplicate, String> {

    private final MemberRepository memberRepository;

    @Override
    public boolean isValid(String memberId, ConstraintValidatorContext context) {
        return !memberRepository.existsById(memberId);
    }
}

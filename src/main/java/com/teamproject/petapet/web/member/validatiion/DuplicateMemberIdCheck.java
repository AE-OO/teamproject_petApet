package com.teamproject.petapet.web.member.validatiion;

import com.teamproject.petapet.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 장사론 22.10.28 작성
 * 로그인용 아이디 중복검사 validation
 * (아이디가 존재할 경우에만 로그인 허용)
 */
@RequiredArgsConstructor
public class DuplicateMemberIdCheck implements ConstraintValidator<DuplicateMemberId, String> {

    private final MemberRepository memberRepository;

    @Override
    public boolean isValid(String memberId, ConstraintValidatorContext context) {
        return memberRepository.existsById(memberId);
    }
}

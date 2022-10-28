package com.teamproject.petapet.validatiion;

import com.teamproject.petapet.domain.member.MemberRepository;
import com.teamproject.petapet.web.member.dto.JoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * 장사론 22.10.28 작성
 * 아이디 중복검사용 validation
 */
@RequiredArgsConstructor
@Component
public class MemberIdDuplicateCheckValidator extends AbstractValidator<JoinDto> {

    private final MemberRepository memberRepository;

    @Override
    protected void doValidate(JoinDto joinDto, Errors errors) {
        if (memberRepository.existsById(joinDto.getMemberId())) {
            errors.rejectValue("memberId", "아이디 중복 오류", "이미 사용중인 아이디 입니다.");
        }
    }

}

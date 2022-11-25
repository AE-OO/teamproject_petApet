package com.teamproject.petapet.web.member.validatiion;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 장사론 22.10.28 작성
 * 회원가입용 아이디 중복검사 validation
 * (아이디가 존재하지 않을 경우에만 회원가입 가능)
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {NotDuplicateMemberIdCheck.class})
public @interface NotDuplicateMemberId {
    String message() default "이미 존재하는 아이디입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

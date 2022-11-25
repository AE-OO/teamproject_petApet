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
 * 로그인용 아이디 중복검사 validation
 * (아이디가 존재할 경우에만 로그인 허용)
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {DuplicateMemberIdCheck.class})
public @interface DuplicateMemberId {
    String message() default "존재하지 않는 아이디입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

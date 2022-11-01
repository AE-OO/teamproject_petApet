package com.teamproject.petapet.validatiion;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 장사론 22.10.28 작성
 * 비밀번호 체크용
 * (아이디가 존재하지 않을 경우에만 회원가입 가능)
 */
@Target({ElementType.TYPE}) //
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MemberPwEqualsCheck.class})
public @interface MemberPwEquals {
    String message() default "비밀번호가 일치하지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

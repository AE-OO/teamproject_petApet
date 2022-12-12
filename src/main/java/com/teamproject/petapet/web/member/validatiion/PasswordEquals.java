package com.teamproject.petapet.web.member.validatiion;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 장사론 22.10.28 작성
 * 비밀번호 체크용
 * 장사론 22.11.02 수정 - 다른 DTO에도 적용가능하게 수정
 */
@Target({ElementType.TYPE}) //
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PasswordEqualsCheck.class})
public @interface PasswordEquals {
    String message() default "비밀번호가 일치하지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String text();
    String comparison();
}

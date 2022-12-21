package com.teamproject.petapet.web.member.validatiion;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {NotDuplicateMemberEmailCheck.class})
public @interface NotDuplicateMemberEmail {
    String message() default "이미 존재하는 이메일 주소입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


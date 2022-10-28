package com.teamproject.petapet.validatiion;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 장사론 22.10.27
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MemberIdDuplicateCheck.class})
public @interface MemberIdDuplicate {
    String message() default "중복 아이디입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

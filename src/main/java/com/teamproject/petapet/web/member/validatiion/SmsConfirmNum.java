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
@Constraint(validatedBy = {SmsConfirmNumCheck.class})
public @interface SmsConfirmNum {
    String message() default "입력하신 번호가 인증번호와 일치하지 않습니다. ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

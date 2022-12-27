package com.teamproject.petapet.web.company.validation;

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
@Constraint(validatedBy = {NotDuplicateCompanyEmailCheck.class})
public @interface NotDuplicateCompanyEmail {
    String message() default "이미 존재하는 이메일 주소입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


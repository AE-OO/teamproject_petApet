package com.teamproject.petapet.web.company.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 장사론 22.10.28 작성
 * 사업자번호 검사 validation
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {NotDuplicateCompanyNumberCheck.class})
public @interface NotDuplicateCompanyNumber {
    String message() default "이미 존재하는 사업자 번호입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

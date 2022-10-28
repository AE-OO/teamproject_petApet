package com.teamproject.petapet.validatiion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 장사론 22.10.28 작성
 * custom validation
 * validation 어노테이션으로는 해결이 안되서 작성함
 * @param <T>
 */
@Slf4j
public abstract class AbstractValidator<T> implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @SuppressWarnings("unchecked") // 컴파일러에서 경고하지 않도록 하기위해 사용
    @Override
    public void validate(Object target, Errors errors) {
        try {
            doValidate((T) target, errors);
        } catch (RuntimeException e) {
            log.error("에러..............", e);
            throw e;
        }
    }
    protected abstract void doValidate(final T dto, final Errors errors);
}


package com.teamproject.petapet.web.member.validatiion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerErrorException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * 장사론 22.10.28 작성
 * 비밀번호 체크용
 * 장사론 22.11.02 수정 - 다른 DTO에도 적용가능하게 수정
 */
@Slf4j
public class PasswordEqualsCheck implements ConstraintValidator<PasswordEquals, Object> {


    private String message;
    private String text;
    private String comparison;

    @Override
    public void initialize(PasswordEquals constraintAnnotation) {
        message = constraintAnnotation.message();
        text = constraintAnnotation.text();
        comparison = constraintAnnotation.comparison();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        String password = getFieldValue(object, text);
        String confirm = getFieldValue(object, comparison);
        if (!password.equals(confirm)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(text)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    // 리플렉션을 이용하여 필드를 가져오는 부분
    private String getFieldValue(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        Field dateField;
        try
        {
            dateField = clazz.getDeclaredField(fieldName);
            dateField.setAccessible(true);
            Object target = dateField.get(object);
            if (!(target instanceof String)) {
                throw new ClassCastException("casting exception");
            }
            return (String) target;
        } catch (NoSuchFieldException e) {
            log.error("NoSuchFieldException", e);
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException", e);
        }
        throw new ServerErrorException("Not Found Field", new Exception().getCause());
    }
}

package com.teamproject.petapet.validatiion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 장사론 22.10.27 작성
 * 인증문자 번호 확인용
 */
@Slf4j
public class SmsConfirmNumCheck implements ConstraintValidator<SmsConfirmNum, String> {
    @Override
    public boolean isValid(String smsConfirmNum, ConstraintValidatorContext context) {
        if(smsConfirmNum == null){
            return true;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if (session.getAttribute("smsConfirmNum") != null) {
//            log.info(session.getAttribute("smsConfirmNum").toString() + "==================="); // 테스트용
            return session.getAttribute("smsConfirmNum").toString().equals(smsConfirmNum);
        }
        return false;
    }
}

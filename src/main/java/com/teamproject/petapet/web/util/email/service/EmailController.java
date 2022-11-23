//package com.teamproject.petapet.web.util;
//
//import com.teamproject.petapet.domain.member.Member;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.mail.MessagingException;
//
////@Controller
//@RequiredArgsConstructor
//public class SmtpController {
//
//    private final EmailService emailService;
//
//    @RequestMapping(value = "/email", method = RequestMethod.POST)
//    @ResponseBody
//    public String sendMail(@RequestBody Member member)throws MessagingException {
//        emailService.sendMail(member);
//        return "Email Sent Successfully.!";
//    }
//
//}

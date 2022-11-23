package com.teamproject.petapet.web.member.service;

public interface EmailService {

    String getTempPassword();
    int emailSend(String email,String memberId);
    int emailSend(String companyId);


}

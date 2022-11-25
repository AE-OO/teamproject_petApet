package com.teamproject.petapet.web.member.service;

public interface EmailService {

    String getTempPassword();
    int emailSend(String email,String memberId);
    int emailSend(String companyId);

    /**
     * 22.11.25 박채원 추가
     * 사업자 가입 거절사유 전송
     */
    int refuseReasonSend(String companyId, String reason);


}

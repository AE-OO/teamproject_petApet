package com.teamproject.petapet.web.member.dto.sms;

import lombok.*;

import java.util.List;

/**
 * 장사론 22.10.23 작성
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SmsRequestDTO {
    private String type;
    private String contentType;
    private String countryCode;
    private String from;
    private String content;
    private List<SmsDTO> messages;
}

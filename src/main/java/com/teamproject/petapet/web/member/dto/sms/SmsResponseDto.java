package com.teamproject.petapet.web.member.dto.sms;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 장사론 22.10.23 작성
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SmsResponseDto {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;

}
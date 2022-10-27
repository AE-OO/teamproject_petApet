package com.teamproject.petapet.web.member.dto.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 장사론 22.10.23 작성
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SmsDto {
    private String to;
    private String content;
}
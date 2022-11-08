package com.teamproject.petapet.web.member.dto.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 장사론 22.10.23 작성
 * 장사론 22.10.27 추가 - validation
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SmsDTO {
    @NotBlank(message = "휴대전화는 필수 입력값입니다.")
    @Pattern(regexp = "^([01]{2})([0|1|6|7|8|9]{1})([0-9]{3,4})([0-9]{4})$",
            message = "형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요.")
    private String to;
    private String content;
}
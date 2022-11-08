package com.teamproject.petapet.web.member.dto;

import lombok.*;
/**
 * 장사론 22.10.19 작성
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {

    private String token;
    private long accessTokenExpiresIn;
}
package com.teamproject.petapet.web.member.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 장사론 22.10.19 작성
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    private String memberId;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100)
    private String memberPw;
}
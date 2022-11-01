package com.teamproject.petapet.web.Inquired.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryDTO {
    private Long inquiryId;

    @NotBlank(message = "제목을 입력해주세요")
    private String inquiryTitle;

    @NotBlank(message = "내용을 입력해주세요")
    private String inquiryContent;
    private String inquiryImg;

    private String inquiryCategory; // 문의 카테고리 default
    private String memberId;
}

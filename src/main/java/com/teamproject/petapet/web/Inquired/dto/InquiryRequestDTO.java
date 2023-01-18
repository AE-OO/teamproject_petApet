package com.teamproject.petapet.web.Inquired.dto;

import lombok.*;

/**
 * 23.01.18 박채원 작성
 */

public class InquiryRequestDTO {

    // 문의사항 답변 등록 DTO
    @Data
    public static class GetAnswerDTO {
        private String answer;
    }
}

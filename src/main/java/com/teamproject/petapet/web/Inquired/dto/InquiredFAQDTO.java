package com.teamproject.petapet.web.Inquired.dto;

import lombok.*;

/**
 * 박채원 22.10.09 작성
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiredFAQDTO {
    private Long inquiredId;
    private String inquiredTitle;
    private String inquiredContent;
    private String inquiredCategory;
    private String memberId;
}

package com.teamproject.petapet.web.Inquired.dto;

import com.teamproject.petapet.domain.inquired.Inquired;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryDTO {
    private Long inquiredId;
    private String inquiredCategory;
    private String inquiredTitle;
    private String memberId;
    private String inquiredDate;
    private boolean checked;

    public static InquiryDTO fromEntityForManageInquiry(Inquired inquired){
        return InquiryDTO.builder()
                .inquiredId(inquired.getInquiredId())
                .inquiredCategory(inquired.getInquiredCategory())
                .inquiredTitle(inquired.getInquiredTitle())
                .memberId(inquired.getMember().getMemberId())
                .inquiredDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(inquired.getInquiredDate()))
                .checked(inquired.isChecked())
                .build();
    }
}

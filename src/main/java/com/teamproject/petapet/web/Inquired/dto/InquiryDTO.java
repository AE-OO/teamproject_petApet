package com.teamproject.petapet.web.Inquired.dto;

import com.teamproject.petapet.domain.inquired.Inquired;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Data
@ToString
@AllArgsConstructor
@Builder
public class InquiryDTO {
    private Long inquiredId;
    private String inquiredCategory;
    private String inquiredTitle;
    private String memberId;
    private String inquiredDate;
    private String inquiredContent;
    private boolean checked;
    private String answer;
    private Long productId;

    public static InquiryDTO fromEntityForManageInquiry(Inquired inquired){
        return InquiryDTO.builder()
                .inquiredId(inquired.getInquiredId())
                .inquiredCategory(inquired.getInquiredCategory())
                .inquiredTitle(inquired.getInquiredTitle())
                .memberId(inquired.getMember().getMemberId())
                .inquiredDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(inquired.getInquiredDate()))
                .inquiredContent(inquired.getInquiredContent())
                .checked(inquired.isChecked())
                .answer(inquired.getAnswer())
                .build();
    }

    public static InquiryDTO fromEntityForOtherInquiry(Inquired inquired){
        return InquiryDTO.builder()
                .inquiredId(inquired.getInquiredId())
                .inquiredTitle(inquired.getInquiredTitle())
                .inquiredCategory(inquired.getInquiredCategory())
                .checked(inquired.isChecked())
                .build();
    }

    public static InquiryDTO fromEntityForProductDetailPage(Inquired inquired){
        return InquiryDTO.builder()
                .inquiredId(inquired.getInquiredId())
                .productId(inquired.getProduct().getProductId())
                .inquiredTitle(inquired.getInquiredTitle())
                .memberId(inquired.getMember().getMemberId())
                .inquiredDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(inquired.getInquiredDate()))
                .inquiredContent(inquired.getInquiredContent())
                .checked(inquired.isChecked())
                .answer(inquired.getAnswer())
                .build();
    }
}

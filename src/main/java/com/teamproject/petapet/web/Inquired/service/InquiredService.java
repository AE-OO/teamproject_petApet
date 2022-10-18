package com.teamproject.petapet.web.Inquired.service;


import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.Inquired.dto.InquiredFAQDTO;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

public interface InquiredService {

    List<Inquired> getFAQ();
    List<Inquired> getOtherInquiries();
    void registerFAQ(InquiredFAQDTO inquiredFAQDTO);
    InquiredFAQDTO getOneFAQ(Long FAQId);
    void updateFAQ(InquiredFAQDTO inquiredFAQDTO);
    void deleteFAQ(Long FAQId);
    default InquiredFAQDTO entityToDTO(Inquired inquired){
        InquiredFAQDTO inquiredFAQDTO = InquiredFAQDTO.builder()
                .inquiredId(inquired.getInquiredId())
                .inquiredTitle(inquired.getInquiredTitle())
                .inquiredContent(inquired.getInquiredContent())
                .inquiredCategory(inquired.getInquiredCategory())
                .memberId(inquired.getMember().getMemberId())
                .build();

        return inquiredFAQDTO;
    }

    default Inquired dtoToEntity(InquiredFAQDTO inquiredFAQDTO){
        Member member = Member.builder().memberId("admin").build();

        Inquired inquired = Inquired.builder()
                .inquiredId(inquiredFAQDTO.getInquiredId())
                .inquiredTitle(inquiredFAQDTO.getInquiredTitle())
                .inquiredContent(inquiredFAQDTO.getInquiredContent())
                .inquiredCategory("FAQ")
                .member(member)
                .build();

        return inquired;
    }
}

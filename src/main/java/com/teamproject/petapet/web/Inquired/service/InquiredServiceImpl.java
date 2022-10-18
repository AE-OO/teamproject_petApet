package com.teamproject.petapet.web.Inquired.service;

import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.domain.inquired.InquiredRepository;
import com.teamproject.petapet.web.Inquired.dto.InquiredFAQDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class InquiredServiceImpl implements InquiredService{

    private final InquiredRepository inquiredRepository;

    @Override
    public List<Inquired> getFAQ() {
        return inquiredRepository.getFAQ();
    }

    @Override
    public List<Inquired> getOtherInquiries() {
        return inquiredRepository.getOtherInquiries();
    }

    @Override
    public void registerFAQ(InquiredFAQDTO inquiredFAQDTO) {
        log.info("========== FAQ 등록 ==========");
        Inquired inquired = dtoToEntity(inquiredFAQDTO);
        inquiredRepository.save(inquired);
    }

    @Override
    public InquiredFAQDTO getOneFAQ(Long FAQId) {
        Inquired inquired = inquiredRepository.getOne(FAQId);
        InquiredFAQDTO inquiredFAQDTO = entityToDTO(inquired);
        return inquiredFAQDTO;
    }

    @Override
    public void updateFAQ(InquiredFAQDTO inquiredFAQDTO) {
        log.info("========== FAQ 수정 ==========");
        Inquired inquired = dtoToEntity(inquiredFAQDTO);
        inquiredRepository.updateFAQ(inquired.getInquiredTitle(), inquired.getInquiredContent(), inquired.getInquiredId());
    }

    @Override
    public void deleteFAQ(Long FAQId) {
        log.info("========== FAQ 삭제 ==========");
        inquiredRepository.deleteById(FAQId);
    }
}

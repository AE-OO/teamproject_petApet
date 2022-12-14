package com.teamproject.petapet.web.Inquired.service;

import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.domain.inquired.InquiredRepository;
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
    public List<Inquired> getMyInquired(String member) {
        return inquiredRepository.findByMemberId(member);
    }

    @Override
    public Inquired inquiredSubmit(Inquired inquired) {
        return inquiredRepository.save(inquired);
    }

    @Override
    public void removeInquiredOne(Long inquiredId) {
        inquiredRepository.deleteById(inquiredId);
    }

//    @Transactional
//    @Override
//    public void setInquiredCheck(Boolean cheked, Long inquiredId) {
//        inquiredRepository.setCheck(cheked,inquiredId);
//    }

    @Override
    public Inquired findOne(Long id) {
        return inquiredRepository.findById(id).get();
    }

    @Override
    public List<Inquired> getOtherInquiries() {
        return inquiredRepository.getOtherInquiries();
    }
}

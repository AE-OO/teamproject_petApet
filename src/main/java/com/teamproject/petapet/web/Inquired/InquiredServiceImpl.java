package com.teamproject.petapet.web.Inquired;

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
    public List<Inquired> getFAQ() {
        return inquiredRepository.getFAQ();
    }

    @Override
    public List<Inquired> getOtherInquiries() {
        return inquiredRepository.getOtherInquiries();
    }
}

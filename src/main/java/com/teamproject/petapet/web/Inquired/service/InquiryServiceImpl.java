package com.teamproject.petapet.web.Inquired.service;

import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.domain.inquired.InquiryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService{

    private final InquiryRepository inquiryRepository;

    @Override
    public Inquired submitInquiry(Inquired inquired) {
        return inquiryRepository.save(inquired);
    }

    @Override
    public List<Inquired> myInquryList() {
        return inquiryRepository.findAll();
    }
}

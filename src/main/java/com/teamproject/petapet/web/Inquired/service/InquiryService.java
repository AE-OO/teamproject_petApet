package com.teamproject.petapet.web.Inquired.service;

import com.teamproject.petapet.domain.inquired.Inquired;

import java.util.List;

public interface InquiryService {

    // 문의하기
    Inquired submitInquiry(Inquired inquired);

    // 문의한 내용 보기 - 멤버
    List<Inquired> myInquryList();
}

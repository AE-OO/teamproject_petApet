package com.teamproject.petapet.web.Inquired.service;


import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.web.Inquired.dto.InquiryDTO;
import com.teamproject.petapet.web.Inquired.dto.InquiryRequestDTO;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

public interface InquiredService {

    List<Inquired> getMyInquired(String member);

    Inquired inquiredSubmit(Inquired inquired);

    void removeInquiredOne(Long inquiredId);

    void setInquiredCheck(Long inquiredId, String answer);

    // 관리자 - VIEW
    Inquired findOne(Long id);
    List<InquiryDTO> getOtherInquiries();

    //회사 Mypage
    List<InquiryDTO> getCompanyPageInquiryList(String companyId);

    //상품 문의 등록
    void registerProductInquiry(InquiryRequestDTO.RegisterInquiryToCompany inquiryRequestDTO);
}

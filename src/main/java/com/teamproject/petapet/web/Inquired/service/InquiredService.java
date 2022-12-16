package com.teamproject.petapet.web.Inquired.service;


import com.teamproject.petapet.domain.inquired.Inquired;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

public interface InquiredService {

    List<Inquired> getMyInquired(String member);

    Inquired inquiredSubmit(Inquired inquired);

    void removeInquiredOne(Long inquiredId);

//    void setInquiredCheck(Long inquiredId);

    // 관리자 - VIEW
    Inquired findOne(Long id);
    List<Inquired> getOtherInquiries();
}

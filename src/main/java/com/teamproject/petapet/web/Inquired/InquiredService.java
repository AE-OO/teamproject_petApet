package com.teamproject.petapet.web.Inquired;


import com.teamproject.petapet.domain.inquired.Inquired;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

public interface InquiredService {

    List<Inquired> getFAQ();

    List<Inquired> getOtherInquiries();
}

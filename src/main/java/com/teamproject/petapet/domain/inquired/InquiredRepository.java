package com.teamproject.petapet.domain.inquired;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

public interface InquiredRepository extends JpaRepository<Inquired, Long> {

    @Query("select i from Inquired i where i.inquiredCategory = 'FAQ'")
    public List<Inquired> getFAQ();

    @Query("select i from Inquired i where i.inquiredCategory like '%문의'")
    public List<Inquired> getOtherInquiries();

    @Modifying
    @Transactional
    @Query("update Inquired i set i.inquiredTitle =:title, i.inquiredContent =:content where i.inquiredId =:FAQId")
    public void updateFAQ(String title, String content, Long FAQId);
}

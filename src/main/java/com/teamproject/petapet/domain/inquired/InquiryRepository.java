package com.teamproject.petapet.domain.inquired;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquired, Long> {

    // 문의하기

    // 문의한 내용 보기 - 멤버
    @Query("select i from Inquired i where i.member =: memberId")
    List<Inquired> myInquiryList(@Param("member") String memberId);
}

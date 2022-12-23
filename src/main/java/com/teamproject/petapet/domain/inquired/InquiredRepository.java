package com.teamproject.petapet.domain.inquired;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

public interface InquiredRepository extends JpaRepository<Inquired, Long> {
    @Query("select i from Inquired i where i.inquiredCategory like '%문의'")
    public List<Inquired> getOtherInquiries();

    // 관리자 페이지 - 고객문의 전체 (최근순)
    @Query("select i from Inquired i where i.member.memberId =:memberId order by i.inquiredId desc ")
    List<Inquired> findByMemberId(@Param("memberId") String memberId);

    // 기업페이지 - 고객문의 승인 (상품문의, 상품주문취소 , 환불 )
    @Transactional
    @Modifying
    @Query(value = "update Inquired i set i.answer =:answer, i.checked = true where i.inquiredId =:inquiredId")
    void replyAnswer(@Param("inquiredId") Long inquiredId ,@Param("answer") String answer);


    List<Inquired> findAllByCompany_CompanyIdOrderByCheckedAscInquiredDate(String companyId);
}

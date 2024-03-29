package com.teamproject.petapet.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("select r from Report r where r.member.memberId is not null and r.responseStatus = false")
    List<Report> getMemberReportList();

    @Query("select r from Report r where r.community.communityId is not null and r.responseStatus = false")
    List<Report> getCommunityReportList();

    @Query("select r from Report r where r.product.productId is not null and r.responseStatus = false")
    List<Report> getProductReportList();
    Report getReportByReportId(Long reportId);
    @Modifying
    @Transactional
    @Query("update Report r set r.responseStatus = 1 where r.reportId = :reportId")
    void setResponseStatusTrue(@Param("reportId") Long reportId);
}

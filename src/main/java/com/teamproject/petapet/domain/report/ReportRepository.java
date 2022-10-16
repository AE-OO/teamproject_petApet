package com.teamproject.petapet.domain.report;

import com.teamproject.petapet.domain.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("select r from Report r where r.member.memberId is not null and r.responseStatus = false")
    List<Report> getMemberReportList();

    @Query("select r from Report r where r.community.communityId is not null and r.responseStatus = false")
    List<Report> getCommunityReportList();
    @Modifying
    @Transactional
    @Query("update Report r set r.responseStatus = 1 where r.reportId =:reportId")
    void setResponseStatusTrue(Long reportId);
}

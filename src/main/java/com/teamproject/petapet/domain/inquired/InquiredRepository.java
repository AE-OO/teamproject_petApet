package com.teamproject.petapet.domain.inquired;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

public interface InquiredRepository extends JpaRepository<Inquired, Long> {
    @Query("select i from Inquired i where i.inquiredCategory like '%문의'")
    public List<Inquired> getOtherInquiries();

    @Query("select i from Inquired i where i.member.memberId =:memberId order by i.inquiredId desc ")
    List<Inquired> findByMemberId(@Param("memberId") String memberId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Inquired i set i.checked = true where i.inquiredId =: inquiredId")
    void setCheck(@Param("inquiredId") Long inquiredId);

    List<Inquired> findAllByCompany_CompanyIdOrderByCheckedAscInquiredDate(String companyId);
}

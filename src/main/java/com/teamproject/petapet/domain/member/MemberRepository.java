package com.teamproject.petapet.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {
    @Modifying
    @Transactional
    @Query("update Member m set m.memberReport = m.memberReport + 1 where m.memberId =:memberId")
    void addMemberReport(String memberId);

    @Modifying
    @Transactional
    @Query("update Member m set m.memberStopDate = current_date where m.memberId =:memberId")
    void updateMemberStopDate(String memberId);

    @Query("select m.memberGender from Member m")
    List<String> getGenderList();
}

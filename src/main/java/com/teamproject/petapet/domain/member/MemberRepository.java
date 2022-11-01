package com.teamproject.petapet.domain.member;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Optional;

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

    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesByMemberId(String memberId);

    @Query(value = "select ifnull(b.cnt, 0) " +
            "from agetable age left outer join (select age , count(age) as cnt " +
            "From (select round((date_format(current_date(), '%Y') - date_format(memberBirthday, '%Y'))/10) as age " +
            "FROM petapet.member " +
            "where memberName not in('admin')) a group by a.age order by a.age) b " +
            "on b.age = age.age", nativeQuery = true)
    List<Integer> getAgeList();

}
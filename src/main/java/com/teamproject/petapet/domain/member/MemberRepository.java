package com.teamproject.petapet.domain.member;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @Modifying
    @Transactional
    @Query("update Member m set m.memberReport = m.memberReport + 1 where m.memberId =:memberId")
    void addMemberReport(@Param("memberId") String memberId);

    @Modifying
    @Transactional
    @Query("update Member m set m.memberStopDate = current_date + 3, m.activated = false where m.memberId =:memberId")
    void updateMemberStopDate(@Param("memberId") String memberId);

    @Query("select m.memberGender from Member m")
    List<String> getGenderList();

    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesByMemberId(String memberId);

    @Query(value = "select ifnull(b.cnt, 0) " +
            "from agetable age left outer join (select age , count(age) as cnt " +
            "From (select round((date_format(current_date(), '%Y') - date_format(memberBirthday, '%Y'))/10) as age " +
            "FROM Member " +
            "where memberName not in('admin')) a group by a.age order by a.age) b " +
            "on b.age = age.age", nativeQuery = true)
    List<Integer> getAgeList();

    @Modifying
    @Transactional
    @Query("update Member m set m.memberPw=:memberPw where m.memberId =:memberId")
    int updateMemberPw(@Param("memberId") String memberId,@Param("memberPw") String memberPw);

    @Modifying
    @Transactional
    @Query("update Member m " +
            "set m.memberBirthday=:memberBirthday, m.memberPhoneNum=:memberPhoneNum, m.memberGender=:memberGender," +
            "m.memberAddress=:memberAddress, m.memberEmail=:memberEmail where m.memberId =:memberId")
    void updateMember(@Param("memberId") String memberId,@Param("memberBirthday") Date memberBirthday,@Param("memberPhoneNum") String memberPhoneNum,
                      @Param("memberGender") String memberGender,@Param("memberAddress")String memberAddress,@Param("memberEmail") String memberEmail);

    @Modifying
    @Transactional
    @Query("update Member m set m.activated=true where m.memberId=:memberId")
    void updateActivated(@Param("memberId") String memberId);

    @Query("select m.memberId from Member m where m.memberName=:memberName and m.memberPhoneNum=:memberPhoneNum")
    Optional<String> findMemberId(@Param("memberName") String memberName,@Param("memberPhoneNum") String memberPhoneNum);

    @Query("select m.memberId from Member m where m.memberId=:memberId and m.memberName=:memberName and m.memberPhoneNum=:memberPhoneNum")
    Optional<String> existMemberId(@Param("memberId") String memberId,@Param("memberName") String memberName,@Param("memberPhoneNum") String memberPhoneNum);

    boolean existsByMemberPhoneNum(String memberPhoneNum);
    boolean existsByMemberEmail(String memberEmail);

    @Modifying
    @Transactional
    @Query("update Member m set m.memberImg=:memberImg where m.memberId=:memberId")
    void updateMemberImg(@Param("memberId") String memberId,@Param("memberImg") String memberImg);

    @Modifying
    @Transactional
    @Query("update Member m set m.memberImg=null where m.memberId=:memberId")
    void deleteMemberImg(@Param("memberId") String memberId);

}
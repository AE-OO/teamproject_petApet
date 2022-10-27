package com.teamproject.petapet.domain.buy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuyRepository extends JpaRepository<Buy, Long> {

    // 구매 목록

    // 선택 삭제

    // 전체 삭제

    /**
     * 추가 구현 기능
     * 구매 후 배송중, 배송완료 배치 프로그램
     * 스케쥴러 update
     */

    /**
     * 회원 구매 목록
     * @param memberId
     * @return
     */
    @Query("select b from Buy b where b.member.memberId =:memberId order by b.buyDate")
    List<Buy> findBuyByMember(@Param("memberId") String memberId);

}

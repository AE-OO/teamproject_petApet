package com.teamproject.petapet.domain.buy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuyRepository extends JpaRepository<Buy, Long> {

    /**
     * 회원 구매 목록
     * @param memberId
     * @return
     */
    @Query("select b from Buy b where b.member.memberId =:memberId order by b.buyDate desc ")
    List<Buy> findBuyByMember(@Param("memberId") String memberId);

    @Query("select CASE WHEN COUNT(b) > 0 THEN true ELSE false END from BuyProduct b where b.product.productId=:productId and exists (select m from Member m where m.memberId=:memberId)")
    boolean existsByBuyIdAndMember(@Param("productId") Long productId, @Param("memberId") String memberId);

    // 전체 상품 월별 판매량 데이터
    @Query(value = "with recursive Dummy as (\n" +
            "   select max(date_sub(now(), interval 5 month)) as startMonth from dual\n" +
            "   union all\n" +
            "   select startMonth + interval 1 month from Dummy where startMonth < now())\n" +
            "select ifnull(R.salesVol, 0) from Dummy d\n" +
            "left outer join\n" +
            "(select bpP.pId, date_format(buyDate, \"%Y-%m\") as buy_date, sum(bpP.quantity) as salesVol from Buy,\n" +
            "    (select p.productId as pId, buyId, quantity as salesVol from BuyProduct bp,\n" +
            "        (select productId from Product where companyId = ?1) p\n" +
            "    where bp.productId = p.productId) bpP\n" +
            "where Buy.buyId = bpP.buyId\n" +
            "group by buy_date) R on date_format(d.startMonth, \"%Y-%m\") = R.buy_date", nativeQuery = true)
    List<Integer> getTotalSalesVolPerMonth(String companyId);

    // 판매량 순위 데이터
    @Query(value = "with recursive Dummy as (\n" +
            "   select 1 as startNum\n" +
            "   union all\n" +
            "   select startNum + 1 from Dummy where startNum < 5)\n" +
            "select j.pname, ifnull(j.salesVol, 0) from Dummy d\n" +
            "left outer join\n" +
            "(select @ROWNUM :=@ROWNUM + 1 as ROWNUM, p.productName as pname, sum(quantity) as salesVol\n" +
            "    from BuyProduct bp, \n" +
            "        (select productId, productName from Product where companyId = ?1) p,\n" +
            "        (select @ROWNUM :=0) tmp\n" +
            "    where bp.productId = p.productId\n" +
            "    group by bp.productId\n" +
            "order by quantity desc limit 5) j on d.startNum = j.ROWNUM;", nativeQuery = true)
    List<List<String>> getSalesVolbyProduct(String companyId);

    // 상품 세부사항의 월별 판매량 데이터
    @Query(value = "with recursive Dummy as (\n" +
            "   select max(date_sub(now(), interval 5 month)) as startMonth from dual\n" +
            "    union all\n" +
            "    select startMonth + interval 1 month from Dummy where startMonth < now())\n" +
            "select ifnull(R.salesVol, 0) from Dummy d\n" +
            "left outer join\n" +
            "(select date_format(buyDate, \"%Y-%m\") as buy_date, sum(bpP.quantity) as salesVol from Buy,\n" +
            "   (select buyId, quantity from BuyProduct bp\n" +
            "   where bp.productId = ?1) bpP\n" +
            "where Buy.buyId = bpP.buyId\n" +
            "group by buy_date) R on date_format(d.startMonth, \"%Y-%m\") = R.buy_date", nativeQuery = true)
    List<Integer> getSalesVolbyProductPerMonth(long productId);

    // 회사 월별 매출 데이터
    @Query(value="with recursive Dummy as (\n" +
            "   select max(date_sub(now(), interval 5 month)) as startMonth from dual\n" +
            "    union all\n" +
            "    select startMonth + interval 1 month from Dummy where startMonth < now())\n" +
            "select ifnull(R.salesVol, 0) from Dummy d\n" +
            "left outer join\n" +
            "(select date_format(buyDate, \"%Y-%m\") as buy_date, sum(bpP.productPrice) as salesVol from Buy,\n" +
            "   (select buyId, bp.productId, productPrice from BuyProduct bp,\n" +
            "       (select productId from Product where companyId = ?1) p\n" +
            "   where bp.productId = p.productId) bpP\n" +
            "where Buy.buyId = bpP.buyId\n" +
            "group by buy_date) R on date_format(d.startMonth, \"%Y-%m\") = R.buy_date;", nativeQuery = true)
    List<Integer> getMonthlySales(String companyId);

}

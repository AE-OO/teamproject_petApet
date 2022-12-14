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
     * 회원 구매 목록
     * @param memberId
     * @return
     */
    @Query("select b from Buy b where b.member.memberId =:memberId order by b.buyDate desc ")
    List<Buy> findBuyByMember(@Param("memberId") String memberId);
    @Query("select CASE WHEN COUNT(b) > 0 THEN true ELSE false END from Buy b where b.product.productId=:buyId and exists (select m from Member m where m.memberId=:memberId)")
    boolean existsByBuyIdAndMember(@Param("buyId") Long buyId, @Param("memberId") String memberId);

    //박채원 22.12.16 추가 (이하 3개 메소드)
    // 전체 상품 월별 판매량 데이터
    @Query(value = "with recursive T as (\n" +
            "\tselect max(date_sub(buyDate, interval 5 month)) as startMonth from buy \n" +
            "\tunion all\n" +
            "    select startMonth + interval 1 month from T where startMonth < now() - interval 1 month\n" +
            ")\n" +
            "select ifnull(R.product,0) from T\n" +
            "left outer join(\n" +
            "select date_format(buyDate, \"%Y-%m\") as buy_date, count(b.productId) as product \n" +
            "from buy b, (select productId from product where companyId = ?1) c \n" +
            "where b.productId in (c.productId)\n" +
            "group by buy_date) R on date_format(T.startMonth, \"%Y-%m\") = R.buy_date;", nativeQuery = true)
    List<Integer> getTotalSalesPerMonth(String companyId);

    //상품별 판매량 데이터
    @Query(value = "with recursive Dummy as (\n" +
            "\tselect 1 as startNum\n" +
            "    union all\n" +
            "    select startNum + 1 from Dummy where startNum < 5\n" +
            ")\n" +
            "select ifnull(j.sales, 0) from Dummy d\n" +
            "left outer join\n" +
            "(select @ROWNUM \\:= @ROWNUM + 1 as ROWNUM, count(*) as sales \n" +
            "from buy b, (select productId from product where companyId = ?1) c, (select @ROWNUM \\:= 0) tmp\n" +
            "where b.productId in (c.productId)\n" +
            "group by b.productId order by sales desc limit 5) j on d.startNum = j.ROWNUM;", nativeQuery = true)
    List<Integer> getProductSales(String companyId);

    //상품별 월별 판매량 데이터
    @Query(value = "with recursive T as (\n" +
            "\tselect max(date_sub(buyDate, interval 5 month)) as startMonth from buy \n" +
            "\tunion all\n" +
            "    select startMonth + interval 1 month from T where startMonth < now() - interval 1 month\n" +
            ")\n" +
            "select ifnull(R.product,0) from T\n" +
            "left outer join(\n" +
            "select date_format(buyDate, \"%Y-%m\") as buy_date, count(b.productId) as product \n" +
            "from buy b \n" +
            "where b.productId = ?1\n" +
            "group by buy_date) R on date_format(T.startMonth, \"%Y-%m\") = R.buy_date;", nativeQuery = true)
    List<Integer> getDetailSalesPerMonth(Long productId);

    List<Buy> getAllByProduct_Company_CompanyIdOrderByBuyDateDesc(String companyId);

    // 월별 회사 매출 데이터
    @Query(value = "with recursive T as (\n" +
            "\tselect max(date_sub(buyDate, interval 11 month)) as startMonth from buy \n" +
            "\tunion all\n" +
            "    select startMonth + interval 1 month from T where startMonth < now() - interval 1 month\n" +
            ")\n" +
            "select ifnull(R.totalPrice,0) from T\n" +
            "left outer join(\n" +
            "select date_format(buyDate, \"%Y-%m\") as buy_date, count(b.productId) as product, sum(b.quantity * c.productPrice) as totalPrice\n" +
            "from buy b, (select productId, productPrice from product where companyId = \"*company111\") c \n" +
            "where b.productId in (c.productId)\n" +
            "group by buy_date) R on date_format(T.startMonth, \"%Y-%m\") = R.buy_date order by date_format(T.startMonth, \"%Y-%m\") desc;", nativeQuery = true)
    List<Integer> getMonthlySales(String companyId);
    
}

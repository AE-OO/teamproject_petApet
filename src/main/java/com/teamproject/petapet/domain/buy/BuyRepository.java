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

    // 박채원 22.12.16 추가 (이하 3개 메소드)
    // 전체 상품 월별 판매량 데이터
    // 오성훈 23.02.12 Buy를 BuyProduct로 정규화로 인해 쿼리 수정(이하 3개)
    @Query(value = "with recursive T as (\n" +
            "            select max(date_sub(now(), interval 5 month)) as startMonth from dual\n" +
            "            union all\n" +
            "            select startMonth + interval 1 month from T where startMonth < now()\n" +
            "            )\n" +
            "            select ifnull(R.product,0) from T\n" +
            "            left outer join(\n" +
            "            select date_format(buyDate, \"%Y-%m\") as buy_date, count(*) as product\n" +
            "            from Buy b, (select bp.productId, bp.quantity, bp.buyId, p.productName\n" +
            "                         from BuyProduct bp,\n" +
            "                              (select p.productId, p.productName from Product p where p.companyId = ?1) as p\n" +
            "                         where bp.productId = p.productId) as c\n" +
            "            where b.buyId in (c.buyId)\n" +
            "            group by buy_date) R on date_format(T.startMonth, \"%Y-%m\") = R.buy_date;", nativeQuery = true)
    List<Integer> getTotalSalesPerMonth(String companyId);

    //상품별 판매량 데이터
    @Query(value = "with recursive Dummy as (select 1 as startNum\n" +
            "                         union all\n" +
            "                         select startNum + 1\n" +
            "                         from Dummy\n" +
            "                         where startNum < 5)\n" +
            "select j.pname, ifnull(j.sales, 0)\n" +
            "from Dummy d\n" +
            "         left outer join\n" +
            "     (select @ROWNUM \\:= @ROWNUM + 1 as ROWNUM, c.productName as pname, count(*) as sales\n" +
            "      from Buy b,\n" +
            "           (select bp.productId, bp.quantity, bp.buyId, p.productName\n" +
            "            from BuyProduct bp,\n" +
            "                 (select p.productId, p.productName from Product p where p.companyId = \"*osh_company\") as p\n" +
            "            where bp.productId = p.productId) as c,\n" +
            "           (select @ROWNUM \\:= 0) tmp\n" +
            "      where b.buyId in (c.buyId)\n" +
            "      group by c.productId\n" +
            "      order by sales desc\n" +
            "      limit 5) j on d.startNum = j.ROWNUM;", nativeQuery = true)
    List<List<String>> getProductSales(String companyId);

    //상품별 월별 판매량 데이터
    @Query(value = "with recursive T as (select max(date_sub(now(), interval 5 month)) as startMonth\n" +
            "                     from dual\n" +
            "                     union all\n" +
            "                     select startMonth + interval 1 month\n" +
            "                     from T\n" +
            "                     where startMonth < now())\n" +
            "select ifnull(R.product, 0)\n" +
            "from T\n" +
            "         left outer join(select date_format(buyDate, \"%Y-%m\") as buy_date, count(bp.productId) as product\n" +
            "                         from Buy b,\n" +
            "                              (select bp.productId, bp.buyId from BuyProduct bp where bp.productId = ?1) as bp\n" +
            "                         where b.buyId in (bp.buyId)\n" +
            "                         group by buy_date) R on date_format(T.startMonth, \"%Y-%m\") = R.buy_date;", nativeQuery = true)
    List<Integer> getDetailSalesPerMonth(Long productId);

    // 월별 회사 매출 데이터
    @Query(value = "with recursive T as (select max(date_sub(now(), interval 11 month)) as startMonth\n" +
            "    from dual\n" +
            "    union all\n" +
            "    select startMonth + interval 1 month\n" +
            "    from T\n" +
            "    where startMonth < now())\n" +
            "    select ifnull(R.totalPrice, 0)\n" +
            "    from T\n" +
            "    left outer join (select date_format(buyDate, \"%Y-%m\")    as buy_date,\n" +
            "    sum(c.quantity * c.productPrice) as totalPrice\n" +
            "    from Buy b,\n" +
            "            (select bp.productId, bp.productPrice, bp.quantity, bp.buyId\n" +
            "    from BuyProduct bp,\n" +
            "            (select p.productId from Product p where p.companyId = ?1) as p\n" +
            "    where bp.productId = p.productId) as c\n" +
            "    where b.buyId in (c.buyId)\n" +
            "    group by buy_date) R on date_format(T.startMonth, \"%Y-%m\") = R.buy_date\n" +
            "    order by date_format(T.startMonth, \"%Y-%m\") desc;", nativeQuery = true)
    List<Integer> getMonthlySales(String companyId);


}

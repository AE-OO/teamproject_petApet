package com.teamproject.petapet.domain.cart;

import com.teamproject.petapet.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 회원조회 : 상품 -> 장바구니  (최근 추가한 순으로 가져오기)
    @Query("select c from Cart c where c.member.memberId = :memberId order by c.cartId desc ")
    List<Cart> findCartByMember(@Param("memberId") String memberId);

    void deleteAllByMember_MemberId(@Param("memberId") String memberId);

    Optional<Cart> findCartByCartIdAndMember(@Param("cartId") Long CartId, @Param("member") Member member);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Cart c set c.quantity = :quantity where c.cartId = :cartId")
    void setQuan(@Param("quantity") Long quantity, @Param("cartId") Long cartId);

    // 카트 상품 중복확인
    @Query("select c.product from Cart c where c.member = :memberId")
    boolean checkDuplication(@Param("memberId") Member memberId);

}

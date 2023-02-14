package com.teamproject.petapet.domain.dibs.repository;

import com.teamproject.petapet.domain.dibs.DibsProduct;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DibsProductRepository extends JpaRepository<DibsProduct, Long> {

    boolean existsDibsProductByProductAndMember(Product productId, Member member);

    Optional<DibsProduct> findByProduct_ProductIdAndMember_MemberId(Long productId, String memberId);

    List<DibsProduct> findAllByMember_MemberId(String memberId);

}

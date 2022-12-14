package com.teamproject.petapet.domain.dibs.repository;

import com.teamproject.petapet.domain.dibs.DibsProduct;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DibsProductRepository extends JpaRepository<DibsProduct, Long> {

    boolean existsDibsProductByProductAndMember(Product productId, Member member);

}

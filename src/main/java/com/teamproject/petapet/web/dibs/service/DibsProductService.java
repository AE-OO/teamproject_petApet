package com.teamproject.petapet.web.dibs.service;

import com.teamproject.petapet.domain.dibs.DibsProduct;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface DibsProductService {

    void saveDibsProduct(DibsProduct dibsProduct);

    void removeDibsProduct(DibsProduct dibsProduct);

    Optional<DibsProduct> findOne(Long productId, Principal principal);

    List<DibsProduct> findDibsListByMemberId(String memberId, Pageable pageable);

    boolean existsDibsProduct(Product id, Member member);
}

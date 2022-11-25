package com.teamproject.petapet.web.dibs.service;

import com.teamproject.petapet.domain.dibs.DibsProduct;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;

public interface DibsProductService {

    void saveDibsProduct(DibsProduct dibsProduct);

    boolean existsDibsProduct(Product id, Member member);
}

package com.teamproject.petapet.web.dibs.service;

import com.teamproject.petapet.domain.dibs.DibsProduct;
import com.teamproject.petapet.domain.dibs.repository.DibsProductRepository;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DibsProductServiceImpl implements DibsProductService {

    private final DibsProductRepository dibsProductRepository;

    @Override
    public void saveDibsProduct(DibsProduct dibsProduct) {
        dibsProductRepository.save(dibsProduct);
    }

    @Override
    public boolean existsDibsProduct(Product id, Member member) {
        return dibsProductRepository.existsDibsProductByProductAndMember(id, member);
    }
}

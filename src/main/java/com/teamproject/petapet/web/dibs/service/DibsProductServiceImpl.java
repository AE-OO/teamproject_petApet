package com.teamproject.petapet.web.dibs.service;

import com.teamproject.petapet.domain.dibs.DibsProduct;
import com.teamproject.petapet.domain.dibs.repository.DibsProductRepository;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DibsProductServiceImpl implements DibsProductService {

    private final DibsProductRepository dibsProductRepository;

    @Override
    @Transactional
    public void saveDibsProduct(DibsProduct dibsProduct) {
        dibsProductRepository.save(dibsProduct);
    }

    @Override
    @Transactional
    public void removeDibsProduct(DibsProduct dibsProduct) {
        dibsProductRepository.delete(dibsProduct);
    }

    @Override
    public Optional<DibsProduct> findOne(Long productId, Principal principal) {
        return dibsProductRepository.findByProduct_ProductIdAndMember_MemberId(productId, principal.getName());
    }

    @Override
    public List<DibsProduct> findDibsListByMemberId(String memberId, Pageable pageable) {
        return dibsProductRepository.findAllByMember_MemberId(memberId);
    }

    @Override
    public boolean existsDibsProduct(Product id, Member member) {
        return dibsProductRepository.existsDibsProductByProductAndMember(id, member);
    }
}

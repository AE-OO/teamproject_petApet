package com.teamproject.petapet.web.buy.service;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.petapet.domain.buy.Buy;
import com.teamproject.petapet.domain.buy.BuyRepository;
import com.teamproject.petapet.domain.buyproduct.BuyProduct;
import com.teamproject.petapet.domain.buyproduct.BuyProductRepository;
import com.teamproject.petapet.web.buyproduct.BuyProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BuyServiceImpl implements BuyService {

    private final BuyRepository buyRepository;
    private final BuyProductRepository buyProductRepository;

    // 구매 목록
    @Override
    public List<Buy> findAll(String member) {
        return buyRepository.findBuyByMember(member);
    }

    @Override
    public Buy findById(Long buyId) {
        return buyRepository.findById(buyId).get();
    }


    // 장바구니 -> 구매
    @Override
    public Buy addBuy(Buy buy) {
        return buyRepository.save(buy);
    }


    @Override
    public boolean existsByPurchaseHistory(Long buyId, String memberId) {
        return buyRepository.existsByBuyIdAndMember(buyId, memberId);
    }

    @Override
    public List<Integer> getTotalSalesVolPerMonth(String companyId) {
        return buyRepository.getTotalSalesVolPerMonth(companyId);
    }

    @Override
    public List<List<String>> getSalesVolbyProduct(String companyId) {
        return buyRepository.getSalesVolbyProduct(companyId);
    }

    @Override
    public List<Integer> getSalesVolbyProductPerMonth(Long productId) {
        return buyRepository.getSalesVolbyProductPerMonth(productId);
    }

    @Override
    public List<BuyProductDTO> getCompanyPageSalesList(String companyId) {
        List<BuyProduct> buyList = buyProductRepository.getAllByProduct_Company_CompanyIdOrderByBuyDesc(companyId);
        return buyList.stream().map(BuyProductDTO::fromEntityForManageSales).collect(Collectors.toList());
    }

    @Override
    public List<Integer> getMonthlySales(String companyId) {
        return buyRepository.getMonthlySales(companyId);
    }

}

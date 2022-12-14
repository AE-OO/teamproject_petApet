package com.teamproject.petapet.web.buy.service;


import com.teamproject.petapet.domain.buy.Buy;
import com.teamproject.petapet.domain.buy.BuyRepository;
import com.teamproject.petapet.web.buy.dto.BuyDTO;
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
    public List<Integer> getTotalSalesPerMonth(String companyId) {
        return buyRepository.getTotalSalesPerMonth(companyId);
    }

    @Override
    public List<Integer> getProductSales(String companyId) {
        return buyRepository.getProductSales(companyId);
    }

    @Override
    public List<Integer> getDetailSalesPerMonth(Long productId) {
        return buyRepository.getDetailSalesPerMonth(productId);
    }

    @Override
    public List<BuyDTO> getCompanyPageSalesList(String companyId) {
        List<Buy> buyList = buyRepository.getAllByProduct_Company_CompanyIdOrderByBuyDateDesc(companyId);
        return buyList.stream().map(list -> BuyDTO.fromEntityForManageSales(list)).collect(Collectors.toList());
    }

    @Override
    public List<Integer> getMonthlySales(String companyId) {
        return buyRepository.getMonthlySales(companyId);
    }

}

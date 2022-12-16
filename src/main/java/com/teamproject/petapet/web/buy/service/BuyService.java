package com.teamproject.petapet.web.buy.service;

import com.teamproject.petapet.domain.buy.Buy;

import java.util.List;

public interface BuyService {

    // 구매 목록
    List<Buy> findAll(String member);

    Buy findById(Long buyId);

    // ->
    Buy addBuy(Buy buy);

    boolean existsByBuyAndMember(Long buyId, String memberId);

    //박채원 22.12.16 추가
    List<Integer> getTotalSalesPerMonth(String companyId);
}

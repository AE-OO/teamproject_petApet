package com.teamproject.petapet.web.buy.service;

import com.teamproject.petapet.domain.buy.Buy;

import java.util.List;

public interface BuyService {

    // 구매 목록
    List<Buy> findAll(String member);

    Buy findById(Long buyId);

    // ->
    Buy addBuy(Buy buy);
}

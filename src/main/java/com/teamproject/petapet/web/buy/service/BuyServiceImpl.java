package com.teamproject.petapet.web.buy.service;


import com.teamproject.petapet.domain.buy.Buy;
import com.teamproject.petapet.domain.buy.BuyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuyServiceImpl implements Buyservice{

    private final BuyRepository buyRepository;

    @Override
    public List<Buy> findAll(String member) {
        return buyRepository.findBuyByMember(member);
    }

    @Override
    public Buy saveBuy(Buy buy) {
        return buyRepository.save(buy);
    }

    // 구매 목록


    // 선택 삭제

    // 전체 삭제
}

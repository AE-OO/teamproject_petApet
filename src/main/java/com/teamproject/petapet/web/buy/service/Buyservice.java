package com.teamproject.petapet.web.buy.service;

import com.teamproject.petapet.domain.buy.Buy;

import java.util.List;

public interface Buyservice {

    // 구매 목록
    List<Buy> findAll(String member);
    // 선택 삭제

    // 전체 삭제
}

package com.teamproject.petapet.web.buy.service;


import com.teamproject.petapet.domain.buy.BuyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuyServiceImpl implements Buyservice{

    public BuyRepository repository;
}

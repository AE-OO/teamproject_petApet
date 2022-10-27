package com.teamproject.petapet.web.cart.service;

import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.cart.CartRepository;
import com.teamproject.petapet.domain.member.MemberRepository;
import com.teamproject.petapet.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    @Override
    public List<Cart> findAll(String memberId) {

        return cartRepository.findCartByMember(memberId);
    }
}

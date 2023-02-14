package com.teamproject.petapet.web.cart.service;


import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.member.Member;

import java.util.List;

public interface CartService {

    List<Cart> findAll(String member);

    Cart addCart(Cart cart);

    Cart findOne(Long cartId);

    Cart findOne(Long cartId, Member member);

    void updateCart(Long cartId, Long quantity);

    void removeCartOne(Long cartId);

    void removeCartAll(String memberId);

    void setQuan(Long quantity, Long cartId);

    boolean checkDuplication(Member memberId);
}

package com.teamproject.petapet.web.cart.service;


import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;

import java.util.List;

public interface CartService {

    List<Cart> findAll(String member);

    Cart addCart(Cart cart);

    Cart findOne(Long cartId);

    void removeCartOne(Long cartId);

    void removeCartAll(String memberId);

    void setQuan(Long quantity, Long cartId);

    boolean checkDuplication(Member memberId);
}

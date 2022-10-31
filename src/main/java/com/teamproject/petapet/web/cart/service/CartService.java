package com.teamproject.petapet.web.cart.service;


import com.teamproject.petapet.domain.cart.Cart;

import java.util.List;

public interface CartService {

    List<Cart> findAll(String member);

    Cart addCart(Cart cart);

    Cart findOne(Long cartId);

}

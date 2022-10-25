package com.teamproject.petapet.web.cart.service;


import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.web.cart.dto.CartDTO;

import java.util.List;

public interface CartService {

    Cart save(Cart cart);

    Cart findOne(Long id);

    List<Cart> findAll(String member);

    Long addCart(Long memberId, Long productId, Long quantity);

    void removeCart(Long cartId);


}

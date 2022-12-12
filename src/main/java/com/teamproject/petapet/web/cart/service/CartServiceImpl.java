package com.teamproject.petapet.web.cart.service;

import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.cart.CartRepository;
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
    @Override
    public Cart addCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart findOne(Long cartId) {
        return cartRepository.findById(cartId).get();
    }

    @Override
    public void removeCartOne(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public void removeCartAll(String memberId) {
        cartRepository.deleteAllByMember(memberId);
    }


}

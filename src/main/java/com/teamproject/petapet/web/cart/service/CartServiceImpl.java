package com.teamproject.petapet.web.cart.service;

import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.cart.CartRepository;
import com.teamproject.petapet.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@Slf4j
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
    public Cart findOne(Long cartId, Member member) {
        return cartRepository.findCartByCartIdAndMember(cartId, member).orElseThrow();
    }

    @Override
    public void updateCart(Long cartId, Long quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(NoSuchElementException::new);
        cart.updateCart(quantity);
    }

    @Override
    public void removeCartOne(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public void removeCartAll(String memberId) {
        cartRepository.deleteAllByMember_MemberId(memberId);
    }

    @Override
    public void setQuan(Long quantity, Long cartId) {
        cartRepository.setQuan(quantity, cartId);
    }

    @Override
    public boolean checkDuplication(Member memberId) {
        return cartRepository.checkDuplication(memberId);
    }
}

package com.teamproject.petapet.web.cart.service;

import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.cart.CartRepository;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.member.MemberRepository;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


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


}

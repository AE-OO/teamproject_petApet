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
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<Cart> findAll(String memberId) {

        return cartRepository.findCartByMember(memberId);
    }
    @Override
    public Cart addCart(Cart cart) {
//        Product product = productRepository.findById(cart.getProduct().getProductId())
//                .orElseThrow(EntityNotFoundException::new);
//        Optional<Member> member = memberRepository.findById(cart.getMember().getMemberId());
//        cartRepository.findCartByMember(member.toString());
        return cartRepository.save(cart);
    }

//    @Override
//    public Cart save(String memberId, Long productId, Long quantity) {
//        return cartRepository.save(memberId, productId, quantity);
//    }
}

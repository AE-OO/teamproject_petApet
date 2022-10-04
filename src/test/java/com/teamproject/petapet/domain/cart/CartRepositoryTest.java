package com.teamproject.petapet.domain.cart;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;

    @Test
    public void insertCartDummies(){
        IntStream.rangeClosed(1, 50).forEach(i -> {
            long productId = (long) (Math.random() * 30) + 1;
            long memberNum = (long) (Math.random() * 30) + 1;

            Product product = Product.builder().productId(productId).build();
            Member member = Member.builder().memberId("memberId" + memberNum).build();

            Cart cart = Cart.builder().member(member).product(product).build();

            cartRepository.save(cart);
        });
    }

}
package com.teamproject.petapet.domain.cart;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.member.MemberRepository;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.web.cart.dto.CartDTO;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    ProductService productService;

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

    @Test
    void test1(){
        String dd = "memberA";
        List<Cart> cartByMember1 = cartRepository.findCartByMember(dd);
        cartByMember1.forEach(i-> System.out.println("i.getCartId() = " + i.getCartId()));
    }

    @Test
    void 카트담기(){
        String member = "memberA";
        Member memberA = memberService.findOne(member);
        Long product = 1L;
        Product product1 = productService.findOne(product).orElseThrow(NoSuchElementException::new);
        Cart cart = new Cart(1L,memberA,product1,1L);
        cartRepository.save(cart);

    }

}
package com.teamproject.petapet.domain.cart;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

@SpringBootTest

class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    ProductService productService;

    @Test
    void arrival() {
        Date today = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd E요일");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf3 = new SimpleDateFormat("a hh:mm:ss");
        SimpleDateFormat sdf4 = new SimpleDateFormat("오늘은 올 해의 D번째 날");
        SimpleDateFormat sdf5 = new SimpleDateFormat("오늘은 이 달의 d번째 날");
        SimpleDateFormat sdf6 = new SimpleDateFormat("오늘은 올 해의 w번째 주");
        SimpleDateFormat sdf7 = new SimpleDateFormat("오늘은 이 달의 W번째 주");
        SimpleDateFormat sdf8 = new SimpleDateFormat("오늘은 이 달의 F번째 E요일");
        SimpleDateFormat afterThreedays = new SimpleDateFormat("내일(E)요일 yy/MM 전");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd (E)", Locale.KOREA);
        System.out.println("afterThreedays = " + afterThreedays);
        System.out.println("sdf8 = " + sdf8);
        System.out.println("newFormat = " + newFormat);
    }

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
    public void modify(){

    }

//    @Test
//    void 카트담기(){
//        String member = "memberA";
//        Member memberA = memberService.findOne(member);
//        Long product = 1L;
//        Product product1 = productService.findOne(product);
//        Cart cart = new Cart(1L,memberA,product1,1L);
//        cartRepository.save(cart);
//
//    }

}
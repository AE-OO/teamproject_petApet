package com.teamproject.petapet.domain.product;

import com.teamproject.petapet.domain.community.Comment;
import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    public void insertReviewDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long productId = (long) (Math.random() * 30) + 1;
            Product product = Product.builder().productId(productId).build();

            Member member = Member.builder().memberId("memberId" + (i % 30 + 1)).build();

            Review review = Review.builder()
                    .reviewTitle("Review " + i)
                    .reviewContent("ReviewContent " + i)
                    .member(member)
                    .product(product)
                    .build();

            reviewRepository.save(review);
        });
    }

    @Test
    public void insertReviewDummiesV2() {
        IntStream.rangeClosed(1, 6).forEach(i -> {
            Product product = productRepository.findById(103L).get();
            Member member = memberRepository.findById("memberId"+i).get();

            Review review = Review.builder()
                    .reviewTitle("Review " + i)
                    .reviewContent("ReviewContent " + i)
                    .member(member)
                    .product(product)
                    .reviewRating(Math.max(0L,3L))
                    .build();

            reviewRepository.save(review);
        });
    }
}
package com.teamproject.petapet.domain.product;

import com.teamproject.petapet.domain.community.Comment;
import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void insertReviewDummies() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
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

}
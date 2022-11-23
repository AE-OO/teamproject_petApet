package com.teamproject.petapet.domain.cart;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import lombok.*;

import javax.persistence.*;

/**
 * 박채원 22.10.02 작성
 */

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"member", "product"})
public class Cart{

    @Id
    @GeneratedValue()
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "productId",nullable = false)
    private Product product;


    // 추가함
    @Column
    private Long quantity;

    public Cart(Member member, Product product, Long quantity) {
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

}

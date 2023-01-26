package com.teamproject.petapet.domain.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.teamproject.petapet.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class CouponBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponBoxId;

    @Column(columnDefinition = "bit default b'1'")
    private boolean isUsed;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime expirationDate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "couponId")
    private Coupon coupons;

    public CouponBox(LocalDateTime expirationDate, Member member, Coupon coupons) {
        this.expirationDate = expirationDate;
        this.member = member;
        this.coupons = coupons;
    }
}

package com.teamproject.petapet.domain.product;

import com.teamproject.petapet.web.product.coupondtos.CouponDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long couponId;
    @Column
    @CreationTimestamp
    private LocalDateTime couponBeginDate;
    @Column
    @CreationTimestamp
    private LocalDateTime couponEndDate;
    @Column
    private Long couponStock;
    @Column
    @Enumerated(EnumType.STRING)
    private ProductType couponAcceptType;
    @Column
    private boolean couponActive;
    @Column(columnDefinition = "bigint(4) default 0")
    private Long couponPriceDisc;
    @Column(columnDefinition = "bigint(2) default 0")
    private Long couponPercentDisc;

    public static Coupon convertToEntity(CouponDTO couponDTO) {
        return Coupon.builder()
                .couponStock(couponDTO.getCouponStock())
                .couponActive(couponDTO.isCouponActive())
                .couponAcceptType(couponDTO.getCouponAcceptType())
                .couponBeginDate(couponDTO.getCouponBeginDate())
                .couponEndDate(couponDTO.getCouponEndDate())
                .couponPercentDisc(couponDTO.getCouponPercentDisc())
                .couponPriceDisc(couponDTO.getCouponPriceDisc())
                .build();
    }

    public CouponDTO convertToDTO() {
        return CouponDTO.builder().couponActive(couponActive)
                .couponAcceptType(couponAcceptType)
                .couponBeginDate(couponBeginDate)
                .couponEndDate(couponEndDate)
                .couponStock(couponStock)
                .couponPercentDisc(couponPercentDisc)
                .couponPriceDisc(couponPriceDisc)
                .couponId(couponId)
                .build();
    }
}

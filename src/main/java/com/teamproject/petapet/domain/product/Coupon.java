package com.teamproject.petapet.domain.product;

import com.teamproject.petapet.web.product.coupon.coupondtos.CouponDTO;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    @Column(length = 45)
    private String couponName;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime couponEndDate;
    @Column
    private Long couponStock;
    @Column(columnDefinition = "varchar(30) default 'ALL'")
    @Enumerated(EnumType.STRING)
    private ProductType couponAcceptType;
    @Column(columnDefinition = "bit default b'1'")
    private boolean couponActive;
    @Column(length = 11)
    private String couponType;
    @Column(length = 5)
    private Long couponDiscRate;

    public void updateCoupon(CouponDTO couponDTO) {
        this.couponId = couponDTO.getCouponId();
        this.couponName = couponDTO.getCouponName();
        this.couponEndDate = LocalDateTime.of(LocalDate.parse(couponDTO.getCouponEndDate()
                        , DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                , LocalTime.of(23, 59));
        this.couponStock = couponDTO.getCouponStock();
        this.couponAcceptType = couponDTO.getCouponAcceptType();
        this.couponActive = couponDTO.isCouponActive();
        this.couponType = couponDTO.getCouponType();
        this.couponDiscRate = couponDTO.getCouponDiscRate();
    }

    public static Coupon convertToEntity(CouponDTO couponDTO) {
        return Coupon.builder()
                .couponStock(couponDTO.getCouponStock())
                .couponActive(couponDTO.isCouponActive())
                .couponAcceptType(couponDTO.getCouponAcceptType())
                .couponEndDate(LocalDateTime.of(LocalDate.parse(couponDTO.getCouponEndDate()
                                , DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        , LocalTime.of(23, 59)))
                .couponType(couponDTO.getCouponType())
                .couponDiscRate(couponDTO.getCouponDiscRate())
                .couponName(couponDTO.getCouponName())
                .build();
    }
}

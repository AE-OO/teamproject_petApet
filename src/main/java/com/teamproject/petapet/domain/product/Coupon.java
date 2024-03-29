package com.teamproject.petapet.domain.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column
    private Long couponAcceptPrice;

    @JsonBackReference
    @OneToMany(mappedBy = "coupons", cascade = CascadeType.REMOVE)
    private List<CouponBox> couponBoxes = new ArrayList<>();

    public Coupon() {
    }

    public Coupon(Long couponId, String couponName, LocalDateTime couponEndDate, Long couponStock, ProductType couponAcceptType, boolean couponActive, String couponType, Long couponDiscRate, Long couponAcceptPrice) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponEndDate = couponEndDate;
        this.couponStock = couponStock;
        this.couponAcceptType = couponAcceptType;
        this.couponActive = couponActive;
        this.couponType = couponType;
        this.couponDiscRate = couponDiscRate;
        this.couponAcceptPrice = couponAcceptPrice;
    }

    public void updateCoupon(CouponDTO couponDTO) {
        this.couponId = couponDTO.getCouponId();
        this.couponName = couponDTO.getCouponName();
        this.couponEndDate = LocalDateTime.of(LocalDate.parse(couponDTO.getCouponEndDate()
                        , DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                , LocalTime.of(23, 59));
        if (couponDTO.getCouponStock() == 0) {
            this.couponStock = couponDTO.getCouponStock();
        } else {
            this.couponStock = couponDTO.getCouponStock() + 1;
        }
        this.couponAcceptType = couponDTO.getCouponAcceptType();
        this.couponActive = couponDTO.isCouponActive();
        this.couponType = couponDTO.getCouponType();
        this.couponDiscRate = couponDTO.getCouponDiscRate();
        this.couponAcceptPrice = couponDTO.getCouponAcceptPrice();
    }

    public Coupon(CouponDTO couponDTO) {
        this.couponName = couponDTO.getCouponName();
        this.couponEndDate = LocalDateTime.of(LocalDate.parse(couponDTO.getCouponEndDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalTime.of(23, 59));
        if (couponDTO.getCouponStock() == 0) {
            this.couponStock = couponDTO.getCouponStock();
        } else {
            this.couponStock = couponDTO.getCouponStock() + 1;
        }
        this.couponAcceptType = couponDTO.getCouponAcceptType();
        this.couponActive = couponDTO.isCouponActive();
        this.couponType = couponDTO.getCouponType();
        this.couponDiscRate = couponDTO.getCouponDiscRate();
    }

}

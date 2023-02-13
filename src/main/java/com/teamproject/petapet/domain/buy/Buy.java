package com.teamproject.petapet.domain.buy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.teamproject.petapet.domain.buyproduct.BuyProduct;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * 박채원 22.10.02 작성
 * 오성훈 23.02.13 List<BuyProduct> 및 비즈니스 로직 추가,
 * Product, quantity는 삭제 예정 (현재 Excel에서 사용 중)
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"member", "product"})
@EntityListeners(value = {AuditingEntityListener.class})
public class Buy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buyId;

    @Column
    private String merchantUid;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime buyDate;

    @Column(length = 45, nullable = false)
    private String buyAddress;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column
    private Long quantity;
    private Long totalPrice;

    @OneToMany(mappedBy = "buy", cascade = ALL)
    private List<BuyProduct> products = new ArrayList<>();

    public void addBuyProducts(BuyProduct buyProduct) {
        products.add(buyProduct);
        buyProduct.updateBuy(this);
    }
    public Buy(String merchantUid, String buyAddress, Member member, List<BuyProduct> buyProducts, Long totalPrice) {
        this.merchantUid = merchantUid;
        this.buyAddress = buyAddress;
        this.member = member;
        for (BuyProduct buyProduct : buyProducts) {
            addBuyProducts(buyProduct);
        }
        this.totalPrice = totalPrice;
    }

    public Buy(String buyAddress, Member member, Product product, Long quantity) {
        this.buyAddress = buyAddress;
        this.member = member;
        this.quantity = quantity;
        this.product = product;
    }

    public Buy(String merchantUid ,String buyAddress, Member member, Product product, Long quantity) {
        this.merchantUid = merchantUid;
        this.buyAddress = buyAddress;
        this.member = member;
        this.quantity = quantity;
        this.product = product;
    }
}

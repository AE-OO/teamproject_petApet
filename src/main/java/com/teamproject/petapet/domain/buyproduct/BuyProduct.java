package com.teamproject.petapet.domain.buyproduct;

import com.teamproject.petapet.domain.buy.Buy;
import com.teamproject.petapet.domain.product.Product;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BuyProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buyProductId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "buyId")
    private Buy buy;

    // 가격은 주문 시점에서 매번 다르기 때문에 컬럼 추가
    private Long productPrice;

    private Long quantity;

    public BuyProduct(Product product, Long productPrice, Long quantity) {
        this.product = product;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public void updateBuy(Buy buy) {
        this.buy = buy;
    }

    public Long getTotalPrice() {
        return getProductPrice() * getQuantity();
    }

}

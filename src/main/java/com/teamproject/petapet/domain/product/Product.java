package com.teamproject.petapet.domain.product;

import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

/**
 * 박채원 22.10.01 작성
 */

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@DynamicInsert
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(length = 45, nullable = false)
    private String productName;

    @Column(length = 5, nullable = false)
    private Long productPrice;

    @Column(columnDefinition = "bigint(5) default 0")
    private Long productStock;

    @ElementCollection
    @CollectionTable(name = "ProductImg", joinColumns = @JoinColumn(name = "productImgId", referencedColumnName = "productId"))
    private List<UploadFile> productImg;

    @Column(length = 45,columnDefinition = "varchar(45) default '판매중'")
    private String productStatus;

    //상품분류
    @Column(length = 45, columnDefinition = "varchar(45)")
    @Enumerated(EnumType.STRING)
    private ProductType productDiv;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String productContent;

    //foreign 키는 Counter 테이블에서 갖지만 Product 테이블에서도 연관관계를 작성해 준 이유는 oneToOne 연관관계는 단방향 관계를 지원하지 않기 때문
    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Counter counter;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Review> review;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Cart> cart;

    public Product(String productName, Long productPrice, Long productStock, List<UploadFile> productImg, String productStatus, ProductType productDiv, String productContent) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productImg = productImg;
        this.productStatus = productStatus;
        this.productDiv = productDiv;
        this.productContent = productContent;
    }
}

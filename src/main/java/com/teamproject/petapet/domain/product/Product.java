package com.teamproject.petapet.domain.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.company.Company;
import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import com.teamproject.petapet.web.product.productdtos.ProductDetailDTO;
import com.teamproject.petapet.web.product.productdtos.ProductInsertDTO;
import com.teamproject.petapet.web.product.productdtos.ProductUpdateDTO;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

/**
 * 박채원 22.10.01 작성
 * 오성훈 22.10.20 productRating 칼럼 추가
 */

@Entity
@Builder
@AllArgsConstructor
@ToString(exclude = {"company", "review", "cart"})
@Getter
@DynamicInsert
@DynamicUpdate
@EntityListeners(value = {AuditingEntityListener.class})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(length = 45, nullable = false)
    private String productName;

    @Column(length = 5, nullable = false)
    private Long productPrice;

    @Column(length = 4, nullable = false)
    private Long productStock;

    @Column(length = 2, nullable = false)
    private Long productDiscountRate;

    @Column(length = 7, nullable = false)
    private Long productUnitPrice;

    @ElementCollection
    @CollectionTable(name = "ProductImg", joinColumns = @JoinColumn(name = "productImgId", referencedColumnName = "productId"))
    private List<UploadFile> productImg;

    @Column(length = 45, columnDefinition = "varchar(45) default '판매중'")
    private String productStatus;

    //상품분류
    @Column(length = 45, columnDefinition = "varchar(45)")
    @Enumerated(EnumType.STRING)
    private ProductType productDiv;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String productContent;

    @Column(length = 1, columnDefinition = "int(1) default 0")
    private Long productRating;

    @Column(columnDefinition = "bigint default 0")
    private Long productReviewCount;

    @Column(columnDefinition = "bigint(3) default 0")
    private Long productReport;

    @Column(columnDefinition = "bigint(5) default 0")
    private Long productViewCount;

    @Column(columnDefinition = "bigint(5) default 0")
    private Long productSellCount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Review> review;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Cart> cart;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Inquired> inquired;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private Company company;

    public Product() {
    }

    public Product(String productName, Long productPrice, Long productStock, Long productDiscountRate, Long productUnitPrice, String productStatus, ProductType productDiv, String productContent, Long productRating, Long productReviewCount, Long productViewCount, Long productSellCount, Company company, List<UploadFile> productImg) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDiscountRate = productDiscountRate;
        this.productUnitPrice = productUnitPrice;
        this.productStatus = productStatus;
        this.productDiv = productDiv;
        this.productContent = productContent;
        this.productRating = productRating;
        this.productReviewCount = productReviewCount;
        this.productViewCount = productViewCount;
        this.productSellCount = productSellCount;
        this.company = company;
        this.productImg = productImg;
    }

    public ProductDetailDTO toProductDetailDTO(Product product) {
        return ProductDetailDTO.builder().productPrice(product.getProductPrice())
                .productImg(product.getProductImg())
                .productId(product.getProductId())
                .productStock(product.getProductStock())
                .productName(product.getProductName())
                .companyId(product.getCompany().getCompanyId())
                .productSeller(product.getCompany().getCompanyName())
                .productContent(product.getProductContent())
                .productDiscountRate(product.getProductDiscountRate())
                .productUnitPrice(product.getProductUnitPrice())
                .productReviewCount(product.getProductReviewCount())
                .build();
    }

    public static Product ConvertToEntityByInsertDTO(ProductInsertDTO insertDTO, List<UploadFile> uploadFiles, ProductType productDiv, Company company) {
        return Product.builder()
                .productName(insertDTO.getProductName())
                .productPrice(insertDTO.getProductPrice())
                .productStock(insertDTO.getProductStock())
                .productImg(uploadFiles)
                .productStatus(insertDTO.getProductStatus())
                .productDiv(productDiv)
                .company(company)
                .productContent(insertDTO.getProductContent())
                .productDiscountRate(insertDTO.getProductDiscountRate())
                .productUnitPrice(insertDTO.getProductUnitPrice())
                .build();
    }

    public Product updateProduct(ProductUpdateDTO productUpdateDTO, List<UploadFile> imgList) {
        this.productName = productUpdateDTO.getProductName();
        this.productPrice = productUpdateDTO.getProductPrice();
        this.productStock = productUpdateDTO.getProductStock();
        this.productDiscountRate = productUpdateDTO.getProductDiscountRate();
        this.productUnitPrice = productUpdateDTO.getProductUnitPrice();
        this.productImg = imgList;
        this.productStatus = productUpdateDTO.getProductStatus();
        this.productContent = productUpdateDTO.getProductContent();
        this.productDiv = ProductType.valueOf(productUpdateDTO.getProductDiv());
        return this;
    }
}


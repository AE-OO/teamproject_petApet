package com.teamproject.petapet.web.product.productdtos;

import com.teamproject.petapet.domain.company.Company;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductType;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class ProductUpdateDTO {

    private Long productId;
    @NotBlank(message = "내용을 입력하세요")
    private String productContent;
    private List<UploadFile> existProductImg;
    private List<MultipartFile> productImg;
    @NotBlank(message = "상품명을 입력하세요")
    private String productName;
    @NotBlank
    private String productSeller;
    @NotNull(message = "1,000원 이상, 9,999,999원 이하의 금액이여야 합니다")
    @Min(value = 1000, message = "1,000원 이상, 9,999,999원 이하의 금액이여야 합니다")
    @Max(value = 9999999, message = "1,000원 이상, 9,999,999원 이하의 금액이여야 합니다")
    private Long productPrice;
    @NotNull(message = "재고를 입력하세요")
    @Min(100)
    @Max(9999)
    private Long productStock;
    @NotNull(message = "할인율를 입력하세요")
    @Min(0)
    @Max(value = 99, message = "0-99까지의 값을 입력하세요")
    private Long productDiscountRate;
    @NotNull(message = "판매가를 입력하세요")
    @Min(value = 1000, message = "1,000원 이상, 9,999,999원 이하의 금액이여야 합니다")
    @Max(value = 9999999, message = "1,000원 이상, 9,999,999원 이하의 금액이여야 합니다")
    private Long productUnitPrice;
    @NotBlank(message = "상품종류를 선택하세요")
    private String productDiv;
    @NotBlank(message = "판매상태를 선택하세요")
    private String productStatus;
    private Long productReviewCount;
    private Long productReport;
    private Long productViewCount;
    private Long productRating;
    private Long productSellCount;
    private List<String> storeFileName;
    private List<String> uploadFileName;

    public static ProductUpdateDTO convertToProductUpdateDTO(Product product) {
        return ProductUpdateDTO.builder().productPrice(product.getProductPrice())
                .existProductImg(product.getProductImg())
                .productId(product.getProductId())
                .productStock(product.getProductStock())
                .productName(product.getProductName())
                .productSeller(product.getCompany().getCompanyName())
                .productContent(product.getProductContent())
                .productDiscountRate(product.getProductDiscountRate())
                .productUnitPrice(product.getProductUnitPrice())
                .productDiv(product.getProductDiv().name())
                .productStatus(product.getProductStatus())
                .productReviewCount(product.getProductReviewCount())
                .productReport(product.getProductReport())
                .productViewCount(product.getProductViewCount())
                .productSellCount(product.getProductSellCount())
                .productRating(product.getProductRating())
                .build();
    }

}

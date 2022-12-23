package com.teamproject.petapet.web.product.productdtos;

import com.teamproject.petapet.domain.company.Company;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductType;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class ProductUpdateDTO {

    private Long productId;
    private String productContent;
    private List<UploadFile> existProductImg;
    private List<MultipartFile> productImg;
    private String productName;
    private String productSeller;
    private Long productPrice;
    private Long productStock;
    private Long productDiscountRate;
    private Long productUnitPrice;
    private String productDiv;
    private String productStatus;
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
                .build();
    }

    public Product convertToEntityByUpdateDTO(ProductUpdateDTO productUpdateDTO, List<UploadFile> imgList, Company company) {
        return Product.builder().productId(productUpdateDTO.getProductId())
                .productImg(imgList)
                .productUnitPrice(productUpdateDTO.getProductUnitPrice())
                .productDiscountRate(productUpdateDTO.getProductDiscountRate())
                .productContent(productUpdateDTO.getProductContent())
                .productDiv(ProductType.valueOf(productUpdateDTO.getProductDiv()))
                .productStatus(productUpdateDTO.getProductStatus())
                .productStock(productUpdateDTO.getProductStock())
                .productPrice(productUpdateDTO.getProductPrice())
                .productName(productUpdateDTO.getProductName())
                .productId(productUpdateDTO.getProductId())
                .company(company)
                .build();
    }
}

package com.teamproject.petapet.web.product.productdtos;

import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
public class ProductInsertDTO {

    @NotBlank(message = "상품명을 입력하세요")
    private String productName;

    @NotNull(message = "1,000원 이상, 9,999,999원 이하의 금액이여야 합니다")
    @Min(value = 1000, message = "1,000원 이상, 9,999,999원 이하의 금액이여야 합니다")
    @Max(value = 9999999, message = "1,000원 이상, 9,999,999원 이하의 금액이여야 합니다")
    private Long productPrice;

    @NotNull(message = "할인율를 입력하세요")
    @Min(0)
    @Max(value = 99, message = "0-99까지의 값을 입력하세요")
    private Long productDiscountRate;

    @NotNull(message = "판매가를 입력하세요")
    @Min(value = 1000, message = "1,000원 이상, 9,999,999원 이하의 금액이여야 합니다")
    @Max(value = 9999999, message = "1,000원 이상, 9,999,999원 이하의 금액이여야 합니다")
    private Long productUnitPrice;

    @NotNull(message = "재고를 입력하세요")
    @Min(100)
    @Max(9999)
    private Long productStock;

    @NotNull
    @NotEmpty
    private List<MultipartFile> productImg;

    @NotBlank(message = "상품종류를 선택하세요")
    private String productDiv;

    @NotBlank(message = "내용을 입력하세요")
    private String productContent;

    @NotBlank
    private String productSeller;

    @NotBlank(message = "판매상태를 선택하세요")
    private String productStatus;

    private List<UploadFile> updateImg;


}

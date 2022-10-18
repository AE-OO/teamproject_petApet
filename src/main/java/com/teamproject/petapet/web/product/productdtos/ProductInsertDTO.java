package com.teamproject.petapet.web.product.productdtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class ProductInsertDTO {

    @NotBlank(message = "상품명을 입력하세요")
    private String productName;

    @NotNull(message = "가격을 입력하세요")
    private Long productPrice;

    @NotNull(message = "재고를 입력하세요")
    @Min(1)
    @Max(9999)
    private Long productStock;

    @NotNull
    @NotEmpty
    private List<MultipartFile> productImg;

    @NotBlank(message = "상품종류를 선택하세요")
    private String productDiv;

    @NotBlank(message = "내용을 입력하세요")
    private String productContent;

}

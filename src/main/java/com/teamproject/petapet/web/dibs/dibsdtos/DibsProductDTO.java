package com.teamproject.petapet.web.dibs.dibsdtos;

import com.teamproject.petapet.domain.product.ProductType;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DibsProductDTO {

    private Long productId;
    private ProductType productType;
    private String productImg;
    private String productName;
    private Long productPrice;
}

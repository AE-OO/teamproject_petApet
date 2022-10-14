package com.teamproject.petapet.web.product.productdtos;

import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductInsertDTO {

    private String productName;
    private Long productPrice;
    private Long productStock;
    @NotNull
    private List<MultipartFile> productImg;
    private String productStatus;
    private String productDiv;
    private String productContent;

}

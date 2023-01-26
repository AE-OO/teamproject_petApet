package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductType;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import com.teamproject.petapet.web.product.productdtos.ProductInsertDTO;
import com.teamproject.petapet.web.product.productdtos.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 박채원 22.10.09 작성
 */

public interface ProductService {
    List<ProductDTO> getProductList(String companyId);

    Page<Product> getProductPage(Pageable pageable);

    Page<Product> getProductListByReview(Pageable pageable);

    void updateProductStatus(String selectStatus, Long productStock, Long productId);

    void updateProductInfo(String type, Long productId, Long productStock, String productStatus);
    Page<Product> findAllByProductDiv(ProductType productType, Pageable pageable);
    void updateProductReviewCount(Long productId, Long reviewCount);
    void updateProductStatusOutOfStock(List<String> productId);

    void updateProductRating(Long productId);

    Optional<Product> findOne(Long id);

    Product findId(Long id);

    Optional<Product> productSave(ProductInsertDTO productInsertDTO,List<UploadFile> uploadFiles);

    Optional<Product> findProductWithReview(Long id);

    Page<Product> findPage(String category,ProductType productType, String sortType,String searchContent,Long starRating, Pageable pageable);
    void addProductReport(Long productId);
}


package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.domain.company.Company;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductType;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import com.teamproject.petapet.web.product.productdtos.ProductDTO;
import com.teamproject.petapet.web.product.productdtos.ProductInsertDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * 박채원 22.10.09 작성
 */

public interface ProductService {
    Page<Product> getProductPage(Pageable pageable);

    Page<Product> getProductListByReview(Pageable pageable);

    void updateProductStatus(String selectStatus, Long productStock, Long productId);

    void updateProductInfo(String type, Long productId, Long productStock, String productStatus);

    void updateProductReviewCount(Long productId, Long reviewCount);

    void updateProductStatusOutOfStock(List<String> productId);

    void updateProductRating(Long productId);

    Optional<Product> findOne(Long id);

    Long compareStock(Long id);

    Optional<Product> saveProduct(ProductInsertDTO productInsertDTO, List<UploadFile> uploadFiles, Company company);

    Page<Product> findPage(@Param("searchContent") String searchContent, Pageable pageable);

    Page<Product> findPage(String category, ProductType productType, String sortType, String searchContent, Long starRating, String minPrice, String maxPrice, String isPriceRange, Pageable pageable);

    void addProductReport(Long productId);
}

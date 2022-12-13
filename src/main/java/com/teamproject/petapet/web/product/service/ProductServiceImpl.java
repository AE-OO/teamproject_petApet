package com.teamproject.petapet.web.product.service;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.repository.ProductRepository;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import com.teamproject.petapet.web.product.productdtos.ProductInsertDTO;
import lombok.RequiredArgsConstructor;

import com.teamproject.petapet.domain.product.ProductType;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.teamproject.petapet.domain.product.QProduct.product;

/**
 * 박채원 22.10.09 작성
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getProductPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getProductListByReview(Pageable pageable) {
        return productRepository.findAllOrderByReviewCount(pageable);
    }

    @Override
    public void updateProductStatus(String selectStatus, Long productStock, Long productId) {
        productRepository.updateProductStatus(selectStatus, productStock, productId);
    }

    @Override
    public Page<Product> findAllByProductDiv(ProductType productType,Pageable pageable) {
        return productRepository.findAllByProductDiv(productType,pageable);
    }

    @Override
    public void updateProductReviewCount(Long productId, Long reviewCount) {
        productRepository.updateProductReviewCount(productId, reviewCount);
    }

    @Override
    public void updateProductStatusOutOfStock(List<String> productId) {
        for(String id : productId){
            productRepository.updateProductStatus("재고없음", 0L, Long.valueOf(id));
        }
    }

    @Override
    public void updateProductRating(Long productId) {
        productRepository.updateProductRating(productId);
    }

    @Override
    public Optional<Product> findOne(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> productSave(ProductInsertDTO insertDTO,List<UploadFile> uploadFiles) {
        ProductType productDiv = ProductType.valueOf(insertDTO.getProductDiv());

        Product product = Product.ConvertToEntityByInsertDTO(insertDTO, uploadFiles, productDiv);

        Product savedProduct = productRepository.save(product);

        return Optional.of(savedProduct);
    }

    @Override
    @Transactional
    public Optional<Product> findProductWithReview(Long id) {
        return productRepository.findProductWithReview(id);
    }

    @Override
    public Page<Product> findPage(String category,ProductType productType, String sortType,String content, Long starRating, String minPrice, String maxPrice,String isPriceRange,Pageable pageable) {
        List<OrderSpecifier> orders = getAllOrderSpecifiers(pageable, sortType);
        List<Product> productList = jpaQueryFactory.select(product)
                .from(product)
                .where(isCategory(productType, category),isContent(content), isRating(starRating), isPriceRange(minPrice,maxPrice,isPriceRange))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.toArray(OrderSpecifier[]::new))
                .fetch();
        long count = productRepository.count();
//        int count = productList.size();
        return new PageImpl<>(productList, pageable, count);
    }

    @Override
    public void addProductReport(Long productId) {
        productRepository.addProductReport(productId);
    }


    private BooleanExpression isContent(String content) {
        if (StringUtils.hasText(content) && !content.equals("false")) {
            return product.productName.contains(content);
        }
        return null;
    }

    private BooleanExpression isCategory(ProductType productType,String category) {
        if (!category.equals("all")) {
            return product.productDiv.eq(productType);
        }
        return null;
    }
    private BooleanExpression isRating(Long rating) {
        if (rating != 0) {
            return product.productRating.goe(rating);
        }
        return null;
    }

    private BooleanExpression isPriceRange(String minPrice, String maxPrice, String isRange) {
        if (isRange.equals("true")) {
        Long parsedMinPrice = minPrice.equals("") ? null : Long.parseLong(minPrice);
        Long parsedMaxPrice = maxPrice.equals("") ? null : Long.parseLong(maxPrice);
            return product.productPrice.between(parsedMinPrice,parsedMaxPrice);
        }
        return null;
    }

    private OrderSpecifier<?> getSorted(Order order, Path<?> parent, String fieldName){
        Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);

        return new OrderSpecifier(order, fieldPath);
    }

    private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable,String sortType) {
        List<OrderSpecifier> ORDERS = new ArrayList<>();

        if (!ObjectUtils.isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                if (order.getProperty().equals(sortType)){
                    OrderSpecifier<?> createdDate = getSorted(direction, product, sortType);
                    ORDERS.add(createdDate);
                }
            }
        }
        return ORDERS;
    }
}

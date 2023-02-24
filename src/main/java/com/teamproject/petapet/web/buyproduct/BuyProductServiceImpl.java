package com.teamproject.petapet.web.buyproduct;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.petapet.domain.buyproduct.BuyProduct;
import com.teamproject.petapet.domain.buyproduct.BuyProductRepository;
import com.teamproject.petapet.web.product.productdtos.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.teamproject.petapet.domain.buyproduct.QBuyProduct.buyProduct;
import static com.teamproject.petapet.domain.product.QProduct.product;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuyProductServiceImpl implements BuyProductService {

    private final BuyProductRepository buyProductRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public BuyProduct save(BuyProduct buyProduct) {
        return buyProductRepository.save(buyProduct);
    }

    @Override
    public List<ProductDTO> getCompanyProductList(String companyId) {
        return jpaQueryFactory.select(Projections.bean(ProductDTO.class,
                        product.productId,
                        product.productName,
                        product.productDiv,
                        product.productPrice,
                        product.productReport,
                        product.productStock,
                        product.productStatus,
                        buyProduct.count().as("totalBuy")))
                .from(product)
                .leftJoin(buyProduct).on(buyProduct.product.productId.eq(product.productId))
                .groupBy(product.productId)
                .where(product.company.companyId.eq(companyId))
                .fetch();
    }

    @Override
    public Long getProduct(Long buyId) {
        return buyProductRepository.findProduct(buyId);
    }
}

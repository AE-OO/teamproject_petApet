package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.domain.company.Company;
import com.teamproject.petapet.domain.company.CompanyRepository;
import com.teamproject.petapet.domain.product.Coupon;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductType;
import com.teamproject.petapet.domain.product.repository.CouponRepository;
import com.teamproject.petapet.domain.product.repository.ProductRepository;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CouponRepository couponRepository;

//    @Test
//    @Commit
    void 더미() {
        LongStream.range(120,130).forEach(e->{
            Company company = companyRepository.findById("*osh_company").get();
            List<UploadFile> img = new ArrayList<>();
            img.add(new UploadFile("640px-Javascript-shield.png", "0d45c459-7a68-4644-b41b-4e3d990c9609.jpg"));
            int i = (int) (Math.random() * 190001) + 10000;
            long ceil = Math.round((i / 100) * 100);

            Product product = new Product("상품_" + e, ceil, 100L, 0L, ceil, "판매중", ProductType.FOOD, "<p>상품_" + e + "<br></p>", 0L, 0L, 0L, 0L, company,img);

            productRepository.save(product);
        });

    }
    @Test
    @Commit
    void 쿠폰더미(){
        LongStream.range(24,46).forEach(e->{
            Coupon coupon = new Coupon(e, "coupon_" + e, LocalDateTime.now().plusDays(31L), 100L, ProductType.ALL, true, "percentDisc", 3000L, 10000L);
            couponRepository.save(coupon);

        });
    }
}
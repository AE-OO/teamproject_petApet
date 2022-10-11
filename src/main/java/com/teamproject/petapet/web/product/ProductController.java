package com.teamproject.petapet.web.product;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public String productMainPage() {
        return "/product/productMainPage";
    }

    @GetMapping("/{food}")
    public String productList(@PathVariable("food") String food){

        return "product/productList";
    }
    @PostMapping("/test")
    public void test2(@Param("test") MultipartFile test) throws IOException, SQLException {
        byte[] bytes = test.getBytes();
        Blob blob = new SerialBlob(bytes);
        log.info("blob = {}", blob.length());
        productRepository.save(new Product("Name",2000L,bytes,"test"));
    }

    @GetMapping("/test2")
    public String test3(Model model) throws IOException, SQLException {
        Optional<Product> byId = productRepository.findById(4L);
        Product product = byId.get();
        byte[] productImg = product.getProductImg();
        log.info("productImg={}", productImg);
        byte[] bytes = Base64.encodeBase64(productImg);
        String s = new String(bytes);
        model.addAttribute("test", s);
        return "/product/blodTest2";
    }
}

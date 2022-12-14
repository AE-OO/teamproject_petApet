package com.teamproject.petapet.web.product;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.domain.product.repository.ReviewRepository;
import com.teamproject.petapet.web.product.productdtos.ProductMainPageListDTO;
import com.teamproject.petapet.web.product.reviewdto.ReviewDTO;
import com.teamproject.petapet.web.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static com.teamproject.petapet.web.product.productdtos.ProductMainPageListDTO.getProductMainPageListDTOS;
import static com.teamproject.petapet.web.product.reviewdto.ReviewDTO.getCollect;

@Slf4j
@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/product")
public class ProductRestController {

    @Value("${editor.img.save.url}")
    private String saveUrl;
    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    @PostMapping(value = "/image", produces = "application/json; charset=utf8")
    public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String originalFileName = multipartFile.getOriginalFilename();    //오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));    //파일 확장자
        String savedFileName = UUID.randomUUID() + extension;    //저장될 파일 명

        File targetFile = new File(saveUrl + savedFileName);
        try {
            InputStream fileStream = multipartFile.getInputStream();
            org.apache.commons.io.FileUtils.copyInputStreamToFile(fileStream, targetFile);
            jsonObject.append("url", "/product/images/" + savedFileName);// 저장할 내부 폴더명 + 파일명
            jsonObject.append("responseCode", "success");

        } catch (IOException e) {
            org.apache.commons.io.FileUtils.deleteQuietly(targetFile);    //저장된 파일 삭제
            jsonObject.append("responseCode", "error");
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @GetMapping("/moreReview")
    public List<ReviewDTO> loadMoreReviewSlice(@RequestParam("productId") Long productId,
                                               @RequestParam("page") Integer page) {

        Sort sort = Sort.by("reviewId").descending();
        Pageable pageable = PageRequest.of(page, 10, sort);
        Slice<Review> requestMoreReview = reviewRepository.requestMoreReview(productId, pageable);

        return getCollect(requestMoreReview);

    }

    @GetMapping("/sort")
    public List<ProductMainPageListDTO> sortIndexProductList(@RequestParam String sortType) {
        if (sortType.equals("review")) {
            Pageable pageable = PageRequest.of(0, 8);
            Page<Product> productList = productService.getProductListByReview(pageable);
            return getProductMainPageListDTOS(productList);
        }
        Sort sort = Sort.by(sortType).descending();
        Pageable pageable = PageRequest.of(0, 8, sort);
        Page<Product> productList = productService.getProductPage(pageable);
        return getProductMainPageListDTOS(productList);
    }

    //22.12.15 박채원 추가 - 이하 1개 메소드(사업자 마이페이지 구현 위함)
    @GetMapping("/manageProduct")
    public ResponseEntity<List<Product>> getProductList(HttpSession session){  //overflow 에러
        return new ResponseEntity<>(productService.getProductList("*company111"), HttpStatus.OK);
    }

}
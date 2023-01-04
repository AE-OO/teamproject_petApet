package com.teamproject.petapet.web.product;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.web.product.fileupload.FileService;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import com.teamproject.petapet.web.product.productdtos.ProductDTO;
import com.teamproject.petapet.web.product.productdtos.ProductMainPageListDTO;
import com.teamproject.petapet.web.product.reviewdto.ReviewDTO;
import com.teamproject.petapet.web.product.reviewdto.ReviewInsertDTO;
import com.teamproject.petapet.web.product.service.ProductService;
import com.teamproject.petapet.web.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.teamproject.petapet.web.product.productdtos.ProductMainPageListDTO.getProductMainPageListDTOS;

@Slf4j
@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/product")
public class ProductRestController {

    @Value("${editor.img.save.url}")
    private String saveUrl;
    private final ReviewService reviewService;
    private final ProductService productService;
    private final FileService fileService;

    @PostMapping(value = "/image", produces = "application/json; charset=utf8")
    public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
        JSONObject jsonObject = new JSONObject();
        String originalFileName = multipartFile.getOriginalFilename();    //오리지날 파일명
        String extension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf(".")) : null;    //파일 확장자
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
        Slice<Review> requestMoreReview = reviewService.requestMoreReview(productId, pageable);

        return reviewService.convertToReviewDTOList(requestMoreReview);

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

    @PostMapping("/updateReview")
    public void updateReview(@ModelAttribute ReviewInsertDTO reviewInsertDTO, @RequestParam("productId") Long productId, Principal principal) throws IOException {
        Review review = reviewService.findOneByMemId(productId, principal.getName()).orElseThrow(NoSuchElementException::new);
        List<UploadFile> storeFiles = fileService.storeFiles(reviewInsertDTO.getReviewImg());
        ArrayList<UploadFile> uploadFiles = new ArrayList<>();
        List<UploadFile> reviewImg = review.getReviewImg();
        if (reviewInsertDTO.getStoreFileName() != null) {
            IntStream.range(0, reviewInsertDTO.getStoreFileName().size()).forEach(i -> {
                UploadFile uploadFile = new UploadFile(reviewInsertDTO.getUploadFileName().get(i), reviewInsertDTO.getStoreFileName().get(i));
                uploadFiles.add(uploadFile);
            });
        }
        uploadFiles.addAll(reviewImg);
        List<UploadFile> uploadFileList = uploadFiles.stream().distinct().collect(Collectors.toList());
        uploadFileList.addAll(storeFiles);

        review.updateReview(reviewInsertDTO.getReviewTitle(), reviewInsertDTO.getReviewContent(), LocalDateTime.now(), reviewInsertDTO.getReviewRating(), uploadFileList);
    }

    @PostMapping("/deleteReviewImg")
    public void deleteReviewImg(@RequestBody String imgData, @RequestParam("productId") Long productId, Principal principal) {
        Review review = reviewService.findOneByMemId(productId, principal.getName()).orElseThrow(NoSuchElementException::new);
        List<UploadFile> reviewImg = review.getReviewImg();
        JSONObject jsonObject = new JSONObject(imgData);
        String substringSrc = jsonObject.optString("substringSrc");
        String attrAlt = jsonObject.optString("attrAlt");
        reviewImg.remove(new UploadFile(attrAlt, substringSrc));
        File targetLocalFile = new File(saveUrl + substringSrc);
        boolean delete = targetLocalFile.delete();
    }

    @PostMapping("/deleteReview")
    public void deleteReview(@RequestBody String productId, Principal principal) {
        Review review = reviewService.findOneByMemId(Long.parseLong(productId), principal.getName()).orElseThrow(NoSuchElementException::new);
        reviewService.deleteReview(review.getReviewId());
    }

    //22.12.15 박채원 추가 - 이하 3개 메소드(사업자 마이페이지 구현 위함)
    @GetMapping(value = "/manageProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDTO>> getProductList(Principal principal) {
        return new ResponseEntity<>(productService.getProductList(principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/updateStock/{productId}")
    public void updateStock(@PathVariable("productId") Long productId, @RequestParam("productStock") Long productStock) {
        productService.updateProductInfo("stock", productId, productStock, null);
    }

    @PostMapping("/updateStatus/{productId}")
    public void updateStatus(@PathVariable("productId") Long productId, @RequestParam("productStatus") String productStatus) {
        productService.updateProductInfo("status", productId, null, productStatus);
    }

}
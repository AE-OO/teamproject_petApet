package com.teamproject.petapet.web.product;

import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.domain.product.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/product")
public class ProductRestController {

    @Value("${editor.img.save.url}")
    private String saveUrl;
    private final ReviewRepository reviewRepository;

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
            jsonObject.append("url", saveUrl + savedFileName);// 저장할 내부 폴더명 + 파일명
            jsonObject.append("responseCode", "success");

        } catch (IOException e) {
            org.apache.commons.io.FileUtils.deleteQuietly(targetFile);    //저장된 파일 삭제
            jsonObject.append("responseCode", "error");
            e.printStackTrace();
        }
        String jsonString = jsonObject.toString();
        return jsonString;
    }

    @GetMapping("/moreReview")
    public List<Review> loadMoreReviewSlice(@RequestParam("productId") Long productId,
                                            @RequestParam("page") Integer page) {
        log.info("loadMoreReviewSlice start");
        log.info("productId={}", productId);
        log.info("page={}", page);
        Sort sort = Sort.by("reviewId").descending();
        Pageable pageable = PageRequest.of(page, 10, sort);
        JSONObject jsonObject = new JSONObject();
        Slice<Review> test = reviewRepository.test(productId, pageable);
        log.info("size={}", test.getContent().size());
        List<Review> content = test.getContent();
        content.forEach(i -> {
            jsonObject.append("toto", i);
            log.info("result={}", i.getReviewId());
        });
//IntStream.rangeClosed(0,9).forEach(i->{
//    jsonObject.append("toto",content.get(i));
//});
        return content;

    }
}
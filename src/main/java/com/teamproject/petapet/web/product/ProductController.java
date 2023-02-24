package com.teamproject.petapet.web.product;

import com.teamproject.petapet.domain.buy.Buy;
import com.teamproject.petapet.domain.company.Company;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductType;
import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.web.buy.service.BuyService;
import com.teamproject.petapet.web.company.service.CompanyService;
import com.teamproject.petapet.web.dibs.service.DibsProductService;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.fileupload.FileService;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import com.teamproject.petapet.web.product.productdtos.ProductDetailDTO;
import com.teamproject.petapet.web.product.productdtos.ProductInsertDTO;
import com.teamproject.petapet.web.product.productdtos.ProductListDTO;
import com.teamproject.petapet.web.product.productdtos.ProductUpdateDTO;
import com.teamproject.petapet.web.product.reviewdto.ReviewDTO;
import com.teamproject.petapet.web.product.reviewdto.ReviewInsertDTO;
import com.teamproject.petapet.web.product.service.ProductService;
import com.teamproject.petapet.web.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    @Value("${editor.img.save.url}")
    private String saveUrl;
    private final ProductService productService;
    private final FileService fileService;
    private final MemberService memberService;
    private final ReviewService reviewService;
    private final DibsProductService dibsProductService;
    private final BuyService buyService;
    private final CompanyService companyService;

    @GetMapping("/main")
    public String productMainPage() {
        return "/product/productMainPage";
    }

    @GetMapping
    public String getProductList(@RequestParam(value = "category", defaultValue = "all") String category,
                                 @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                 @RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
                                 @RequestParam(value = "sortType", defaultValue = "productId", required = false) String sortType,
                                 @RequestParam(value = "searchContent", defaultValue = "false", required = false) String content,
                                 @RequestParam(value = "isPriceRange", defaultValue = "false", required = false) String isPriceRange,
                                 @RequestParam(value = "minPrice", defaultValue = "", required = false) String minPrice,
                                 @RequestParam(value = "maxPrice", defaultValue = "", required = false) String maxPrice,
                                 @RequestParam(value = "rating", defaultValue = "0", required = false) Long starRating,
                                 Model model, Principal principal) {

        Sort sort = getSort(sortType);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        String property = pageable.getSort().get().findFirst().orElseThrow(NoSuchElementException::new).getProperty();
        ProductType productType = getProductType(category);
        Page<Product> resultPage = productService.findPage(category, productType, property, content, starRating, minPrice, maxPrice, isPriceRange, pageable);
        Page<ProductListDTO> productListDTO = getProductListDTO(principal, resultPage);

        model.addAttribute("productList", productListDTO);
        model.addAttribute("productDiv", productType);
        model.addAttribute("category", category);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("starRating", starRating);
        model.addAttribute("sortType", sortType);
        model.addAttribute("searchContent", content);
        model.addAttribute("isPriceRange", isPriceRange);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        return "product/productList";
    }

    @GetMapping("/insert")
    public String addProductForm(@ModelAttribute("ProductInsertDTO") ProductInsertDTO productInsertDTO) {
        return "/product/productInsertForm";
    }

    @PostMapping("/insert")
    public String addProduct(@Validated @ModelAttribute("ProductInsertDTO") ProductInsertDTO productInsertDTO,
                                BindingResult bindingResult, Principal principal) {
        if (productInsertDTO.getProductImg().get(0).isEmpty()) {
            bindingResult.addError(new FieldError("productInsertDTO", "productImg", "1장 이상의 사진을 올려주세요"));
        }

        if (!productInsertDTO.getProductSeller().equals(principal.getName())) {
            bindingResult.addError(new FieldError("productInsertDTO", "productSeller", "판매자명이 잘못됐습니다."));
        }

        if (bindingResult.hasErrors()) {
            return "/product/productInsertForm";
        }

        Company company = companyService.findById(principal.getName()).orElseThrow(NoSuchElementException::new);

        List<MultipartFile> productImg = productInsertDTO.getProductImg();
        List<UploadFile> uploadFiles = fileService.storeFiles(productImg);

        Product savedProduct = productService.saveProduct(productInsertDTO, uploadFiles, company)
                .orElseThrow(NoSuchElementException::new);

        String redirectURL = "/product/" +
                savedProduct.getProductDiv().name().toLowerCase() + "/" +
                savedProduct.getProductId() + "/" + "details";

        return "redirect:" + redirectURL;
    }

    @GetMapping("/update")
    public String updateProductForm(@ModelAttribute("ProductUpdateDTO") ProductUpdateDTO productUpdateDTO
            , @RequestParam("productId") Long productId, Model model, Principal principal) {
        Product findProduct = productService.findOne(productId).orElseThrow(NoSuchElementException::new);
        productUpdateDTO = ProductUpdateDTO.convertToProductUpdateDTO(findProduct);
        if (!principal.getName().equals(findProduct.getCompany().getCompanyId())) {
            throw new IllegalStateException("권한이 없는 접근입니다.");
        }
        model.addAttribute("ProductUpdateDTO", productUpdateDTO);
        return "/product/productUpdateForm";
    }

    @PostMapping("/update")
    @ResponseBody
    @Transactional
    public ResponseEntity<?> updateProduct(@Validated @ModelAttribute("ProductUpdateDTO") ProductUpdateDTO productUpdateDTO,
                                           BindingResult bindingResult,
                                           Principal principal) {
        HttpHeaders headers = new HttpHeaders();

        if (bindingResult.hasGlobalErrors()) {
        }
        if (!productUpdateDTO.getProductSeller().equals(principal.getName())) {
            bindingResult.addError(new FieldError("productInsertDTO", "productSeller", "판매자명이 잘못됐습니다."));
        }

        if (bindingResult.hasErrors()) {
            headers.setLocation(URI.create("/update?productId=" + productUpdateDTO.getProductId()));
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        Product findProduct = productService.findOne(productUpdateDTO.getProductId()).orElseThrow(NoSuchElementException::new);

        List<UploadFile> updateImages = updateImage(productUpdateDTO, findProduct);

        Product updateProduct = findProduct.updateProduct(productUpdateDTO, updateImages);

        String redirectURL = "/product/" +
                updateProduct.getProductDiv().name().toLowerCase() + "/" +
                updateProduct.getProductId() + "/" + "details";

        headers.setLocation(URI.create(redirectURL));

        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }

    @GetMapping(value = "/images/{filename}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String filename) throws IOException {
        String fullPath = fileService.getFullPath(filename);
        MediaType mediaType = MediaType.parseMediaType(Files.probeContentType(Paths.get(fullPath)));
        UrlResource resource = new UrlResource("file:" + fullPath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, mediaType.toString())
                .body(resource);
    }

    @GetMapping("/{productType}/{productId}/details")
    public String detailViewForm(@PathVariable("productType") String productType
            , @PathVariable("productId") Long productId
            , Principal principal, Model model) {
        Product findProduct = productService.findOne(productId).orElseThrow(NoSuchElementException::new);
        findProduct.increaseViewCount();
        ProductDetailDTO productDetailDTO = findProduct.toProductDetailDTO(findProduct);
        Sort sort = Sort.by("reviewId").descending();
        Slice<Review> reviews = reviewService.requestMoreReview(productId, PageRequest.of(0, 10, sort));
        model.addAttribute("findProduct", productDetailDTO);
        model.addAttribute("reviews", reviews);

        if (principal != null) {
            boolean existsDibsProduct = dibsProductService.existsDibsProduct(findProduct, memberService.findOne(principal.getName()));
            model.addAttribute("existsDibsProduct", existsDibsProduct);
            boolean existsByPurchaseHistory = buyService.existsByPurchaseHistory(productId, principal.getName());
            model.addAttribute("existsByPurchaseHistory", existsByPurchaseHistory);
            boolean existByReviewHistory = reviewService.existByReviewHistory(productId, principal.getName());
            model.addAttribute("existByReviewHistory", existByReviewHistory);
            if (existByReviewHistory) {
                Review existReview = reviewService.findOneByMemId(productId, principal.getName()).orElseThrow(NoSuchElementException::new);
                ReviewDTO reviewDTO = Review.toReviewDTO(existReview);
                model.addAttribute("existReview", reviewDTO);
            }
        }

        return "/product/productDetails";
    }


    @PostMapping("/{productId}/reviewInsert")
    public String insertReview(@ModelAttribute ReviewInsertDTO reviewInsertDTO,
                               @RequestParam String requestURI,
                               @PathVariable("productId") Long productId,
                               Principal principal) {

        List<MultipartFile> reviewImg = reviewInsertDTO.getReviewImg();
        List<UploadFile> uploadFiles = fileService.storeFiles(reviewImg);

        Member member = memberService.findOne(principal.getName());
        Product product = productService.findOne(productId).orElseThrow(NoSuchElementException::new);

        Review review = Review.buildReview(reviewInsertDTO, uploadFiles, member, product);

        reviewService.save(review);

        Long countReviewByProduct = reviewService.countReviewByProduct(productId);
        productService.updateProductRating(productId);
        productService.updateProductReviewCount(productId, countReviewByProduct);
        return "redirect:" + requestURI;
    }

    private Page<ProductListDTO> getProductListDTO(Principal principal, Page<Product> productList) {
        Page<ProductListDTO> productListDTOS;
        if (principal != null) {
            Member member = memberService.findOne(principal.getName());
            productListDTOS = productList.map(m -> ProductListDTO.builder().productName(m.getProductName())
                    .productPrice(m.getProductPrice())
                    .productImg(m.getProductImg())
                    .productId(m.getProductId())
                    .productDiv(m.getProductDiv())
                    .productRating(m.getProductRating())
                    .productDiscountRate(m.getProductDiscountRate())
                    .productUnitPrice(m.getProductUnitPrice())
                    .productReviewCount(m.getProductReviewCount())
                    .duplicateDibsProduct(dibsProductService.existsDibsProduct(productService.findOne(m.getProductId()).orElseThrow(NoSuchElementException::new), member))
                    .review(m.getReview()).build());
        } else {
            productListDTOS = productList.map(m -> ProductListDTO.builder().productName(m.getProductName())
                    .productPrice(m.getProductPrice())
                    .productImg(m.getProductImg())
                    .productId(m.getProductId())
                    .productDiv(m.getProductDiv())
                    .productRating(m.getProductRating())
                    .productDiscountRate(m.getProductDiscountRate())
                    .productReviewCount(m.getProductReviewCount())
                    .productUnitPrice(m.getProductUnitPrice())
                    .review(m.getReview()).build());
        }
        return productListDTOS;
    }

    private ProductType getProductType(String category) {
        category = category.toUpperCase();
        return ProductType.valueOf(category);
    }


    private Sort getSort(String sortType) {
        Sort sort = Sort.by(sortType).descending();
        if (sortType.equals("salePriceAsc")) {
            sort = Sort.by("productPrice").ascending();
        } else if (sortType.equals("salePriceDesc")) {
            sort = Sort.by("productPrice").descending();
        }
        return sort;
    }

    private List<UploadFile> updateImage(ProductUpdateDTO productUpdateDTO, Product findProduct) {
        List<UploadFile> existProductImg = new ArrayList<>();
        if (productUpdateDTO.getStoreFileName() != null) {
            IntStream.range(0, productUpdateDTO.getStoreFileName().size()).forEach(i -> {
                UploadFile uploadFile = new UploadFile(productUpdateDTO.getUploadFileName().get(i), productUpdateDTO.getStoreFileName().get(i));
                existProductImg.add(uploadFile);
            });
        }
        // 영속성 객체 이미지
        List<UploadFile> findProductImg = findProduct.getProductImg();
        if (existProductImg.size() != findProductImg.size()) {
            findProductImg.removeAll(existProductImg);
            for (UploadFile uploadFile : findProductImg) {
                File localFile = new File(saveUrl + uploadFile.getStoreFileName());
                localFile.delete();
            }
        }
        // 새로 받아 온 이미지
        List<MultipartFile> productImg = productUpdateDTO.getProductImg();
        if (!productImg.get(0).isEmpty()) {
            List<UploadFile> newUploadFiles = fileService.storeFiles(productUpdateDTO.getProductImg());
            existProductImg.addAll(newUploadFiles);
        }
        return existProductImg;
    }
}




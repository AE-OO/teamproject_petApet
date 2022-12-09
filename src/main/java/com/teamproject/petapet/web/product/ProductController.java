package com.teamproject.petapet.web.product;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductType;
import com.teamproject.petapet.domain.product.QProduct;
import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.web.buy.service.BuyService;
import com.teamproject.petapet.web.dibs.service.DibsProductService;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.productdtos.ProductDetailDTO;
import com.teamproject.petapet.web.product.productdtos.ProductListDTO;
import com.teamproject.petapet.web.product.productdtos.ReviewInsertDTO;
import com.teamproject.petapet.web.product.service.ProductService;
import com.teamproject.petapet.web.product.fileupload.FileService;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import com.teamproject.petapet.web.product.productdtos.ProductInsertDTO;
import com.teamproject.petapet.web.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static com.teamproject.petapet.domain.product.QProduct.product;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final FileService fileService;
    private final MemberService memberService;
    private final ReviewService reviewService;
    private final DibsProductService dibsProductService;
    private final BuyService buyService;
    private final JPAQueryFactory jpaQueryFactory;

    @GetMapping("/main")
    public String productMainPage() {
        return "/product/productMainPage";
    }

    @GetMapping
    public String getProductList(@RequestParam(value = "category", defaultValue = "all") String category,
                                 @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                 @RequestParam(value = "size", defaultValue = "2", required = false) Integer size,
                                 @RequestParam(value = "sortType", defaultValue = "productId", required = false) String sortType,
                                 @RequestParam(value = "searchContent",required = false) String content,
                                 Model model, Principal principal) {
        Sort sort = Sort.by(sortType).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        ProductType productType = getProductType(category);
        Page<Product> page1 = productService.findPage(category, productType, sortType,content, pageable);
        Page<ProductListDTO> productListDTO1 = getProductListDTO(principal, page1);
        model.addAttribute("productList",productListDTO1);
        model.addAttribute("productDiv", productType);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sortType", sortType);
        return "product/productList";
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam("category") String category,
                                @RequestParam("searchContent") String content,
                                @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                @RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
                                Model model, Principal principal) {
        ProductType productType = getProductType(category);
        List<Product> productList = jpaQueryFactory.query().select(product).from(product).where(isContent(content),isCategory(productType,category)).orderBy(product.productId.desc()).fetch();
        PageImpl<Product> products = convertToPage(productList,page,size);
        getProductListDTO(principal, products);
        model.addAttribute("productDiv", productType);
        model.addAttribute("searchContent", content);
        return "product/productList";
    }
    @GetMapping("/insert")
    public String productInsertForm(@ModelAttribute("ProductInsertDTO") ProductInsertDTO productInsertDTO) {
        return "/product/productInsertForm";
    }

    @PostMapping("/insert")
    public String productInsert(@Validated @ModelAttribute("ProductInsertDTO") ProductInsertDTO productInsertDTO, BindingResult bindingResult) throws IOException {

        if (productInsertDTO.getProductImg().get(0).isEmpty()) {
            bindingResult.addError(new FieldError("productInsertDTO", "productImg", "1장 이상의 사진을 올려주세요"));
        }

        if (bindingResult.hasErrors()) {
            return "/product/productInsertForm";
        }

        List<MultipartFile> productImg = productInsertDTO.getProductImg();
        List<UploadFile> uploadFiles = fileService.storeFiles(productImg);

        ProductType productDiv = ProductType.valueOf(productInsertDTO.getProductDiv());

        Product product = new Product(productInsertDTO.getProductName()
                , productInsertDTO.getProductPrice()
                , productInsertDTO.getProductStock()
                , uploadFiles
                , "판매중"
                , productDiv
                , productInsertDTO.getProductContent()
                , productInsertDTO.getProductDiscountRate()
                , productInsertDTO.getProductUnitPrice());
        productService.productSave(product);

        String redirectURL = "/product/" +
                product.getProductDiv().name().toLowerCase() + "/" +
                product.getProductId() + "/" + "details";

        return "redirect:" + redirectURL;
    }

    @GetMapping(value = "/images/{filename}")
    public ResponseEntity<Resource> downloadImageV2(@PathVariable String filename) throws IOException {
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
        ProductDetailDTO productDetailDTO = findProduct.toProductDetailDTO(findProduct);
        Sort sort = Sort.by("reviewId").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Slice<Review> reviews = reviewService.requestMoreReview(productId, pageable);
        model.addAttribute("findProduct", productDetailDTO);
        model.addAttribute("reviews", reviews);
        if (principal != null) {
            boolean existsDibsProduct = dibsProductService.existsDibsProduct(findProduct, memberService.findOne(principal.getName()));
            model.addAttribute("existsDibsProduct", existsDibsProduct);
            boolean existsByBuyAndMember = buyService.existsByBuyAndMember(productId, principal.getName());
            model.addAttribute("existsByBuyAndMember", existsByBuyAndMember);
        }
        return "/product/productDetails";
    }


    @PostMapping("/{productId}/reviewInsert")
    public String reviewInsert(@ModelAttribute ReviewInsertDTO reviewInsertDTO,
                               @RequestParam String requestURI, Principal principal,
                               @PathVariable("productId") Long productId) throws IOException {

        List<MultipartFile> reviewImg = reviewInsertDTO.getReviewImg();
        List<UploadFile> uploadFiles = fileService.storeFiles(reviewImg);

        Member member = memberService.findOne(principal.getName());

        Review review = Review.builder().reviewTitle(reviewInsertDTO.getReviewTitle())
                .reviewRating(reviewInsertDTO.getReviewRating())
                .reviewContent(reviewInsertDTO.getReviewContent())
                .reviewImg(uploadFiles)
                .reviewDate(LocalDateTime.now())
                .member(member)
                .product(productService.findOne(productId).orElseThrow(NoSuchElementException::new)).build();

        reviewService.save(review);
        Long aLong = reviewService.countReviewByProduct(productId);
        productService.updateProductRating(productId);
        productService.updateProductReviewCount(productId,aLong);
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

    private BooleanExpression isContent(String content) {
        if (StringUtils.hasText(content)) {
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
    private PageImpl<Product> convertToPage(List<Product> productList,Integer page,Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        long totalCount = productList.size();
        boolean hasNext = false;
        if (productList.size() > pageable.getPageSize()) {
            productList.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new PageImpl<>(productList, pageable, totalCount);
    }
}


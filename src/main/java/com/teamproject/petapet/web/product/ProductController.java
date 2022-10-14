package com.teamproject.petapet.web.product;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductRepository;
import com.teamproject.petapet.domain.product.ProductType;
import com.teamproject.petapet.domain.product.service.ProductService;
import com.teamproject.petapet.web.product.fileupload.FileService;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import com.teamproject.petapet.web.product.productdtos.ProductInsertDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final FileService fileService;

    @GetMapping
    public String productMainPage() {
        return "/product/productMainPage";
    }

    @GetMapping("/{category}")
    public String productList(@PathVariable("category") String category, Model model) {
        category = category.toUpperCase();
        ProductType productType = ProductType.valueOf(category);
        List<Product> productList = productService.findAllByProductDiv(productType);
        model.addAttribute("productList", productList);
        model.addAttribute("productDiv", productType.getProductCategory());
        return "product/productList";
    }

    @GetMapping("/insert")
    public String productInsertForm() {
        return "/product/productInsertForm";
    }

    @PostMapping("/insert")
    public String productInsert(@ModelAttribute("ProductInsertDTO") ProductInsertDTO productInsertDTO) throws IOException {

        List<MultipartFile> productImg = productInsertDTO.getProductImg();
        List<UploadFile> uploadFiles = fileService.storeFiles(productImg);

        ProductType productDiv = ProductType.valueOf(productInsertDTO.getProductDiv());

        Product product = new Product(productInsertDTO.getProductName()
                , productInsertDTO.getProductPrice()
                , productInsertDTO.getProductStock()
                , uploadFiles
                , productInsertDTO.getProductStatus()
                , productDiv
                , productInsertDTO.getProductContent());

        productService.productSave(product);

        String redirectURL = "/product/" +
                product.getProductDiv().name().toLowerCase() + "/" +
                product.getProductId() + "/" + "details";

        return "redirect:" + redirectURL;
    }

//    @GetMapping(value = "/Users/oh/Desktop/test/file/{fileName}",produces = MediaType.IMAGE_PNG_VALUE)
//    @ResponseBody
//    public Resource downloadImage(@PathVariable String filename) throws
//            MalformedURLException {
//        return new UrlResource("file:" + fileService.getFullPath(filename));
//    }

    @GetMapping(value = "/images/{filename}")
    public ResponseEntity<Resource> downloadImageV2(@PathVariable String filename) throws IOException {
        log.info("path={}", filename);
        String fullPath = fileService.getFullPath(filename);
        MediaType mediaType = MediaType.parseMediaType(Files.probeContentType(Paths.get(fullPath)));
        UrlResource resource = new UrlResource("file:" + fullPath);
        ResponseEntity<Resource> body = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, mediaType.toString())
                .body(resource);
        log.info("body={}", body);
        return body;
    }

    @GetMapping("/{productType}/{productId}/details")
    public String detailViewForm(@PathVariable("productType") String productType
            , @PathVariable("productId") Long productId) {

        return "/product/productDetails";
    }
}

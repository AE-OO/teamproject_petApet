package com.teamproject.petapet.web.Inquired.controller;

import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.web.Inquired.dto.InquiryDTO;
import com.teamproject.petapet.web.Inquired.dto.InquiryRequestDTO;
import com.teamproject.petapet.web.Inquired.service.InquiredService;
import com.teamproject.petapet.web.member.dto.CommunityMemberDTO;
import com.teamproject.petapet.web.product.service.ProductService;
import com.teamproject.petapet.web.util.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class InquiredRestController {

    private final InquiredService inquiredService;

    private final ProductService productService;
    private final EmailService emailService;

    // 사업자 마이페이지에 문의 리스트 띄우기
    @GetMapping(value = "/manageInquiry", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InquiryDTO>> getInquiryList(Principal principal){
        return new ResponseEntity<>(inquiredService.getCompanyPageInquiryList(principal.getName()), HttpStatus.OK);
    }

    // 사업자 마이페이지에서 문의 답변하기
    @PostMapping("/addAnswer/{inquiredId}")
    public void addAnswer(@PathVariable("inquiredId") Long inquiredId, @ModelAttribute("inquiredList") InquiryRequestDTO.GetAnswerDTO getAnswerDTO) throws Exception {
        inquiredService.setInquiredCheck(inquiredId, getAnswerDTO.getAnswer());
        emailService.sendEmailMessage2(inquiredService.findOne(inquiredId).getMember().getMemberEmail(), inquiredId);
    }
    // 사업자 마이페이지에서 환불 후 답변하기
//    @PostMapping("/getCanclePayment")
//    public void cancelPayment(@PathVariable("inquiredId") Long inquiredId, @ModelAttribute("inquiredList") InquiryRequestDTO.GetCancleDTO getCancleDTO) throws Exception {
//        Inquired inquired = inquiredService.findOne(getCancleDTO.getInquiredId());
//        Long productStock = inquired.getProduct().getProductStock();
//        Long productId = inquired.getProduct().getProductId();
//        Long productPrice = inquired.getProduct().getProductPrice();
//        long cancleAmount  = productPrice * productStock;
//        String answerReply = cancleAmount + "원 환불완료 되셨습니다 " ;
//
//        inquiredService.setInquiredCheck(inquiredId, answerReply);
//        productService.canclePayment(productStock,productId);
//
//        emailService.sendEmailMessage2(inquiredService.findOne(inquiredId).getMember().getMemberEmail(), inquiredId);
//    }


    // 상품 상세 페이지에서 상품에 대한 문의 등록하기
    @PostMapping("/registerProductInquiry")
    public ResponseEntity<String> registerProductInquiry(@RequestBody InquiryRequestDTO.RegisterInquiryToCompany inquiryRequestDTO){
        inquiredService.registerProductInquiry(inquiryRequestDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    
    // 상품 상세 페이지에 상품에 대한 문의 리스트 띄우기
    @GetMapping(value="/getProductInquiry/{productId}/{pageNum}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<InquiryDTO>> getProductDetailPageInquiryList(@PathVariable("productId") Long productId, @PathVariable("pageNum") int pageNum){
        return new ResponseEntity<>(inquiredService.getProductDetailPageInquiryList(productId, pageNum), HttpStatus.OK);
    }
}

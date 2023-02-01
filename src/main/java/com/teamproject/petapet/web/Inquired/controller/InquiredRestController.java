package com.teamproject.petapet.web.Inquired.controller;

import com.teamproject.petapet.web.Inquired.dto.InquiryDTO;
import com.teamproject.petapet.web.Inquired.dto.InquiryRequestDTO;
import com.teamproject.petapet.web.Inquired.service.InquiredService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class InquiredRestController {

    private final InquiredService inquiredService;

    // 사업자 마이페이지에 문의 리스트 띄우기
    @GetMapping(value = "/manageInquiry", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InquiryDTO>> getInquiryList(Principal principal){
        return new ResponseEntity<>(inquiredService.getCompanyPageInquiryList(principal.getName()), HttpStatus.OK);
    }

    // 사업자 마이페이지에서 문의 답변하기
    @PostMapping("/addAnswer/{inquiredId}")
    public void addAnswer(@PathVariable("inquiredId") Long inquiredId, @ModelAttribute("inquiredList") InquiryRequestDTO.GetAnswerDTO getAnswerDTO){
        inquiredService.setInquiredCheck(inquiredId, getAnswerDTO.getAnswer());
    }

    // 상품 상세 페이지에서 상품에 대한 문의 등록하기
    @PostMapping("/registerProductInquiry")
    public ResponseEntity<String> registerProductInquiry(@RequestBody InquiryRequestDTO.RegisterInquiryToCompany inquiryRequestDTO){
        inquiredService.registerProductInquiry(inquiryRequestDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    
    // 상품 상세 페이지에 상품에 대한 문의 리스트 띄우기
    @GetMapping(value="/getProductInquiry/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InquiryDTO>> getProductDetailPageInquiryList(@PathVariable("productId") Long productId){
        return new ResponseEntity<>(inquiredService.getProductDetailPageInquiryList(productId), HttpStatus.OK);
    }
}

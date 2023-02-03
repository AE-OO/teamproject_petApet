package com.teamproject.petapet.web.Inquired.controller;

import com.teamproject.petapet.web.Inquired.dto.InquiryDTO;
import com.teamproject.petapet.web.Inquired.dto.InquiryRequestDTO;
import com.teamproject.petapet.web.Inquired.service.InquiredService;
import com.teamproject.petapet.web.util.email.service.EmailService;
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
}

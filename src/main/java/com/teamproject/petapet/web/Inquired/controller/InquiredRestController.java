package com.teamproject.petapet.web.Inquired.controller;

import com.teamproject.petapet.web.Inquired.dto.InquiryDTO;
import com.teamproject.petapet.web.Inquired.service.InquiredService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class InquiredRestController {

    private final InquiredService inquiredService;

    @GetMapping(value = "/manageInquiry", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InquiryDTO>> getInquiryList(Principal principal){
        return new ResponseEntity<>(inquiredService.getCompanyPageInquiryList(principal.getName()), HttpStatus.OK);
    }
}

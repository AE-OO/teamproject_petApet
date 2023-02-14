package com.teamproject.petapet.web.Inquired;


import com.teamproject.petapet.domain.buy.Buy;
import com.teamproject.petapet.domain.company.Company;
import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.Inquired.dto.InquiredSubmitDTO;
import com.teamproject.petapet.web.Inquired.service.InquiredService;
import com.teamproject.petapet.web.buy.service.BuyService;
import com.teamproject.petapet.web.company.service.CompanyService;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.fileupload.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class InquiredController {

    private final InquiredService inquiredService;

    private final MemberService memberService;

    private final CompanyService companyService;

    private final FileService fileService;

    private final BuyService buyService;

    public final String INQUIRED_CATEGORY1 = "기업 문의";

    @GetMapping()
    public String getMyInquiry(@ModelAttribute("Inquired") InquiredSubmitDTO inquiredSubmitDTO,
                          Principal principal, Model model){
        String loginMember = checkMember(principal);
        List<Inquired> myInquiry = inquiredService.getMyInquired(loginMember);
        model.addAttribute("myInquiry", myInquiry);
        List<Buy> buyList = buyService.findAll(loginMember);
        model.addAttribute("buyList", buyList);
        return "mypage/inquiry";
    }

//    @GetMapping
//    public String dd(){
//        return "mypage/inquiry";
//    }

    @PostMapping("/submit")
    public String inquirySubmit(@Validated @ModelAttribute("Inquired") InquiredSubmitDTO inquiredSubmitDTO,
                                   Principal principal) {
        log.info("실행");
        String login = checkMember(principal);
        Member loginMember = memberService.findOne(login);


        Inquired inquired = new Inquired(
                inquiredSubmitDTO.getTitle(),
                inquiredSubmitDTO.getInquiredContent(),
                INQUIRED_CATEGORY1,
                loginMember,
                companyService.findOne(inquiredSubmitDTO.getCompanyId()).orElseThrow(NoSuchElementException::new),
                false
        );

        inquiredService.inquiredSubmit(inquired);

        return "redirect:/";
    }


    private String checkMember(Principal principal) {
        return memberService.findOne(principal.getName()).getMemberId();
    }
}

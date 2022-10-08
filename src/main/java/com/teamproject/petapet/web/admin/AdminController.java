package com.teamproject.petapet.web.admin;

import com.teamproject.petapet.domain.member.MemberRepository;
import com.teamproject.petapet.web.Inquired.InquiredService;
import com.teamproject.petapet.web.community.CommunityService;
import com.teamproject.petapet.web.member.MemberService;
import com.teamproject.petapet.web.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 박채원 22.10.09 작성
 */

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final InquiredService inquiredService;
    private final ProductService productService;
    private final CommunityService communityService;
    private final MemberService memberService;

    @GetMapping("/adminPage")
    public String adminPage(Model model){

        model.addAttribute("FAQ", inquiredService.getFAQ());
        model.addAttribute("otherInquiry", inquiredService.getOtherInquiries());
        model.addAttribute("product", productService.getProductList());
        model.addAttribute("community", communityService.getProductList());
        model.addAttribute("member", memberService.getMemberList());
        return "/admin/adminMain";
    }

}

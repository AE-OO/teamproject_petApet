package com.teamproject.petapet.web.admin;

import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.web.Inquired.service.InquiredService;
import com.teamproject.petapet.web.community.dto.CommunityRequestDTO;
import com.teamproject.petapet.web.community.service.CommunityService;
import com.teamproject.petapet.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * 박채원 22.10.09 작성
 */

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final InquiredService inquiredService;
    private final CommunityService communityService;
    private final MemberService memberService;

    @GetMapping("/adminPage")
    public String adminPage(){
        return "/admin/adminMain";
    }

    // 뷰 확인
    @GetMapping("/{idx}/edit")
    public String inquiryView(@PathVariable("idx") Long inquiredId, Model model){
        Inquired inquiredList = inquiredService.findOne(inquiredId);
        model.addAttribute("inquiredList", inquiredList);
        log.info("뷰 완료!!");
        return "admin/inquiryView";
    }
//     뷰 업데이트
//    @PostMapping("/{idx}/edit")
//    public String updateCheckInquiry(@PathVariable("idx") Long inquiredId, @ModelAttribute InquiryDTO inquiryDTO){
//
//        inquiredService.setInquiredCheck(inquiredId);
//        log.info("수정 완료!!");
//        return "redirect:/admin/adminPage";
//    }

    //공지사항 등록 폼으로 이동
    @GetMapping("/registerNotice")
    public String registerNoticeForm(){
        return "/admin/registerNotice";
    }

    //공지사항 등록
    @PostMapping("/registerNotice") 
    public String registerNotice(CommunityRequestDTO.registerNotice registerNotice){
        communityService.registerNotice(registerNotice);
        return "redirect:/admin/adminPage";
    }

    //공지사항 수정 폼으로 이동
    @GetMapping("/updateNoticeForm/{noticeId}")
    public String getOneFAQ(@PathVariable("noticeId") Long noticeId, Model model){
        model.addAttribute("noticeInfo",communityService.getOneNotice(noticeId));
        return "/admin/updateNotice";
    }

    //공지사항 수정
    @PostMapping("/updateNotice")
    public String updateFAQ(CommunityRequestDTO.registerNotice registerNotice){
        communityService.updateNotice(registerNotice);
        return "redirect:/admin/adminPage";
    }


//    @GetMapping("/registerProduct")
//    public String registerProductForm(){
//        return "/admin/registerProduct";
//    }
//
//    @PostMapping("/registerProduct")
//    public String registerProduct(){
//        return "redirect:/admin/adminPage";
//    }

//    @ResponseBody
//    @RequestMapping(value = "/updateProductStatus/{productId}", method = RequestMethod.GET)
//    public void updateProductStatus(@RequestParam Map<String, Object> map, @PathVariable("productId") Long productId){
//        productService.updateProductStatus((String)map.get("status"), Long.valueOf((String) map.get("stock")), productId);
//    }

    //회원 강제탈퇴 기능 구현
    @GetMapping("/deleteMember/{memberId}")
    public void deleteMember(@PathVariable("memberId") String memberId){
        memberService.deleteMember(memberId);
    }
}
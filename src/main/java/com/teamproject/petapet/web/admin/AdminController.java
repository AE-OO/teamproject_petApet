package com.teamproject.petapet.web.admin;

import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.web.Inquired.dto.InquiryRequestDTO;
import com.teamproject.petapet.web.Inquired.service.InquiredService;
import com.teamproject.petapet.web.community.dto.CommunityRequestDTO;
import com.teamproject.petapet.web.community.service.CommunityService;
import com.teamproject.petapet.web.util.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;


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

    private final EmailService emailService;

    @GetMapping("/adminPage")
    public String adminPage(){
        return "/admin/adminMain";
    }

    // 문의사항 상세페이지 띄우기
    @GetMapping("/{idx}/edit")
    public String inquiryView(@PathVariable("idx") Long inquiredId, Model model){
        Inquired inquiredList = inquiredService.findOne(inquiredId);
        model.addAttribute("inquiredList", inquiredList);
        return "admin/inquiryView";
    }
    
    // 문의사항에 답변 달기
    @PostMapping("/{idx}/edit")
    public String updateCheckInquiry(@PathVariable("idx") Long inquiredId, @ModelAttribute("inquiredList") InquiryRequestDTO.GetAnswerDTO getAnswerDTO) throws Exception {
        inquiredService.setInquiredCheck(inquiredId, getAnswerDTO.getAnswer());
        emailService.sendEmailMessage2(getAnswerDTO.getAnswer(),inquiredId); // 문의 내용 이메일 전송
        return "redirect:/admin/adminPage";
    }

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
}
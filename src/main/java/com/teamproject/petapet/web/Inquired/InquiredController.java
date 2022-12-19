package com.teamproject.petapet.web.Inquired;


import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.Inquired.dto.InquiredSubmitDTO;
import com.teamproject.petapet.web.Inquired.service.InquiredService;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.fileupload.FileService;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import com.teamproject.petapet.web.util.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class
InquiredController {

    private final InquiredService inquiredService;

    private final MemberService memberService;

    private final FileService fileService;

    private final EmailService emailService;

    public final String INQUIRED_CATEGORY1 = "문의";


    @GetMapping()
    public String getMyInquiry(@ModelAttribute("Inquired") InquiredSubmitDTO inquiredSubmitDTO,
                          Principal principal, Model model){
        String loginMember = checkMember(principal);
        List<Inquired> myInquiry = inquiredService.getMyInquired(loginMember);
        model.addAttribute("myInquiry", myInquiry);
        return "mypage/inquiry";
    }

    // 회원이 문의 하는 기능
    @PostMapping("/submit")
    public String inquirySubmit(@Validated
                                  @ModelAttribute("Inquired") InquiredSubmitDTO inquiredSubmitDTO,
                                   Principal principal) throws IOException {
        log.info("실행");
//        List<MultipartFile> inquiredImg = inquiredSubmitDTO.getInquiredImg();
//        List<UploadFile> uploadFiles = fileService.storeFiles(inquiredImg);
        String login = checkMember(principal);
        Member loginMember = memberService.findOne(login);


        Inquired inquired = new Inquired(
                inquiredSubmitDTO.getTitle(),
                inquiredSubmitDTO.getInquiredContent(),
                INQUIRED_CATEGORY1,
                loginMember,
                inquiredSubmitDTO.getEmail(),
                false
        );

        inquiredService.inquiredSubmit(inquired);

        return "redirect:/mypage/inquiry";
    }

    // 관리자가 답변하는 기능 - 완료
    @PostMapping("/answer")
    private String setAnswer(@ModelAttribute("adminDTO") InquiredSubmitDTO.AdminDTO adminDTO) throws Exception {
        inquiredService.replyAnswer(adminDTO.getInquiredId(), adminDTO.getAnswer());
        log.info("=> 답변 승인 완료");
        Inquired inquired = inquiredService.findOne(adminDTO.getInquiredId());
        emailService.sendEmailMessage2(inquired.getEmail(), inquired.getInquiredId());
        log.info("=> 메일 송신 승인");
        return "redirect:";
    }

    private String checkMember(Principal principal) {
        return memberService.findOne(principal.getName()).getMemberId();
    }

}

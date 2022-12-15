package com.teamproject.petapet.web.member.controller;

import com.teamproject.petapet.web.member.dto.MemberRequestDTO;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.fileupload.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.security.Principal;
import java.util.UUID;

/**
 * 장사론 22.10.19 작성
 */
@RestController
@RequiredArgsConstructor
public class MemberRestController {

    @Value("${editor.img.save.url}")
    private String saveUrl;

    private final MemberService memberService;
    private final FileService fileService;

    //아이디 중복검사
    @PostMapping("/checkId")
    boolean duplicateCheckMemberId(@RequestParam String memberId) {
        return memberService.duplicateCheckMemberId(memberId);
    }

    //비밀번호 확인용
    @PostMapping("/checkPw")
    boolean checkMemberPw(Principal principal, @RequestParam String memberPw){
        return memberService.checkMemberPw(principal.getName(),memberPw);
    }

    @PostMapping("/checkEmail")
    boolean checkMemberEmail(@RequestParam String memberEmail){
        return memberService.duplicateCheckMemberEmail(memberEmail);
    }

    @PostMapping("/checkPhoneNum")
    boolean checkMemberPhoneNum(@RequestParam String memberPhoneNum){
        return memberService.duplicateCheckMemberPhoneNum(memberPhoneNum);
    }

    //인증번호 확인용
    @PostMapping("/checkSmsConfirmNum")
    boolean checkSmsConfirmNum(@RequestParam String smsConfirmNum, HttpSession session){
        if (session.getAttribute("smsConfirmNum") != null) {
            return session.getAttribute("smsConfirmNum").toString().equals(smsConfirmNum);
        }
        return false;
    }

    @PostMapping("/updateMemberPw")
    int updateMemberPw(Principal principal,@Valid @RequestBody MemberRequestDTO.UpdateMemberPwDTO updateMemberPwDTO){
        if(!memberService.checkMemberPw(principal.getName(),updateMemberPwDTO.getNewMemberPw())) {
            return memberService.updateMemberPw(principal.getName(), updateMemberPwDTO.getNewMemberPw());
        }
        return 0;
    }

    @PostMapping("/updateMemberImg")
    void updateMemberImg(Principal principal,@RequestParam String originalMemberImg,@RequestParam MultipartFile memberImg) throws IOException {
        System.out.println("aaaaaa");
        System.out.println(originalMemberImg);
        System.out.println(fileService.storeFile(memberImg).toString());
//        if(memberService.getOriginalMemberImg(principal.getName()) != null){
//        fileService.deleteFile(memberService.getOriginalMemberImg(principal.getName()));
//           }
    }

}

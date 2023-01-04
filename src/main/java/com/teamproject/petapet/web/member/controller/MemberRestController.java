package com.teamproject.petapet.web.member.controller;

import com.teamproject.petapet.web.community.commentDto.CommentDTO;
import com.teamproject.petapet.web.member.dto.MemberRequestDTO;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.fileupload.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    boolean checkMemberPw(Principal principal, @RequestParam String memberPw) {
        return memberService.checkMemberPw(principal.getName(), memberPw);
    }

    @PostMapping("/checkEmail")
    boolean checkMemberEmail(@RequestParam String memberEmail) {
        return memberService.duplicateCheckMemberEmail(memberEmail);
    }

    @PostMapping("/checkPhoneNum")
    boolean checkMemberPhoneNum(@RequestParam String memberPhoneNum) {
        return memberService.duplicateCheckMemberPhoneNum(memberPhoneNum);
    }

    //인증번호 확인용
    @PostMapping("/checkSmsConfirmNum")
    boolean checkSmsConfirmNum(@RequestParam String smsConfirmNum, HttpSession session) {
        if (session.getAttribute("smsConfirmNum") != null) {
            return session.getAttribute("smsConfirmNum").toString().equals(smsConfirmNum);
        }
        return false;
    }

    @PostMapping("/updateMemberPw")
    int updateMemberPw(Principal principal, @Valid @RequestBody MemberRequestDTO.UpdateMemberPwDTO updateMemberPwDTO) {
        if (!memberService.checkMemberPw(principal.getName(), updateMemberPwDTO.getNewMemberPw())) {
            return memberService.updateMemberPw(principal.getName(), updateMemberPwDTO.getNewMemberPw());
        }
        return 0;
    }

    @PostMapping("/updateMemberImg")
    void updateMemberImg(Principal principal, @RequestParam(required = false) MultipartFile memberImg) throws IOException {
        fileService.deleteFile(memberService.getOriginalMemberImg(principal.getName()));
        if (memberImg == null) {
            memberService.deleteMemberImg(principal.getName());
        } else {
            memberService.updateMemberImg(principal.getName(), fileService.storeFile(memberImg).getStoreFileName());
        }
    }

    //로그인된 아이디,권한 보내기
    @PostMapping("/getLoginId")
    public ResponseEntity<String[]> getLoginId(Principal principal) {
        //로그인 안되어있을 경우 빈값 보냄
        if(!(principal instanceof Principal)){
            return new ResponseEntity<>(new String[]{}, HttpStatus.OK);
        }
        Object principal2 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal2;
        return new ResponseEntity<>(new String[]{userDetails.getUsername(), userDetails.getAuthorities().toString()}, HttpStatus.OK);
    }

}

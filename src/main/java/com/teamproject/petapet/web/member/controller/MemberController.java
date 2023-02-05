package com.teamproject.petapet.web.member.controller;

import com.teamproject.petapet.jwt.JwtAuthenticationFilter;
import com.teamproject.petapet.web.member.dto.MemberDTO;
import com.teamproject.petapet.web.member.dto.MemberRequestDTO;
import com.teamproject.petapet.web.member.dto.TokenDTO;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.fileupload.FileService;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 장사론 22.10.20 작성
 */
@Slf4j
@Controller
@RequiredArgsConstructor
//@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final FileService fileService;

    //회원가입
    @GetMapping("/join")
    public String joinForm(Principal principal, MemberRequestDTO.JoinDTO joinDTO) {
        if (principal instanceof Principal) {
            return "redirect:/";
        }
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid MemberRequestDTO.JoinDTO joinDTO, BindingResult bindingResult, Model model, HttpSession session) {
        // 회원가입 요청시 member입력 값에서 Validation에 걸리는 경우
        if (bindingResult.hasErrors()) {
            //회원가입 실패시 입력 데이터 유지
            model.addAttribute("joinDTO", joinDTO);
            //회원가입 실패시 message 값들을 모델에 매핑해서 View로 전달
            Map<String, String> validateResult = memberService.validateHandling(bindingResult);
            for (String key : validateResult.keySet()) {
                model.addAttribute(key, validateResult.get(key));
            }
            return "join";
        }
        session.removeAttribute("smsConfirmNum");
        memberService.join(joinDTO);
        return "redirect:/";
    }

    //로그인
    @GetMapping("/login")
    public String loginForm(Principal principal) {
        if (principal instanceof Principal) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid MemberRequestDTO.LoginDTO loginDTO, BindingResult bindingResult, Model model, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            Map<String, String> validateResult = memberService.validateHandling(bindingResult);
            for (String key : validateResult.keySet()) {
                model.addAttribute(key, validateResult.get(key));
            }
            return "login";
        }

        TokenDTO tokenDTO = memberService.login(loginDTO);
        //토큰 쿠키에 저장
        Cookie cookie = new Cookie(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer" + tokenDTO.getToken());
        cookie.setPath("/");
//        cookie.setMaxAge(60 * 60 * 24); //유효기간 24시간
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return "redirect:/";
    }

    //아이디 찾기
    @GetMapping("/findId")
    public String findMemberIdForm(){ return "findId"; }

    @PostMapping("/findMemberId")
    public String findMemberId(@Valid MemberRequestDTO.FindMemberIdDTO findMemberIdDTO,BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            Map<String, String> validateResult = memberService.validateHandling(bindingResult);
            for (String key : validateResult.keySet()) {
                model.addAttribute(key, validateResult.get(key));
            }
            return "findId";
        }
        model.addAttribute("findMemberId",memberService.findMemberId(findMemberIdDTO));
        return "findId";
    }

    //비밀번호 찾기
    @GetMapping("/findPw")
    public String findMemberPwForm(){ return "findPw";}

    @PostMapping("/findMemberPw")
    public String findMemberPw(@Valid MemberRequestDTO.FindMemberPwDTO findMemberPwDTO,BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            Map<String, String> validateResult = memberService.validateHandling(bindingResult);
            for (String key : validateResult.keySet()) {
                model.addAttribute(key, validateResult.get(key));
            }
            return "findPw";
        }
        model.addAttribute("memberId",memberService.findMemberPw(findMemberPwDTO));
        return "findPw";
    }

    //회원정보 수정 전 비밀번호 확인
    @GetMapping("/member/checkInfo")
    public String myInfo(){return "member/checkInfo";}

    @PostMapping("/member/checkInfo")
    public String myInfo(Principal principal, Model model){
        model.addAttribute("memberDTO", memberService.memberInfo(principal.getName()));
        return "member/modifyInfo";
    }

    //회원정보 수정
    @PostMapping("/member/modifyInfo")
    public String modifyInfo(Principal principal, @Valid MemberRequestDTO.UpdateMemberInfo updateMemberInfo,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/member/checkInfo";
        }
        memberService.updateMemberInfo(principal.getName(),updateMemberInfo);
        return "redirect:/member/checkInfo";
    }

    //회원탈퇴
    @GetMapping("/member/withdrawal")
    public String withdrawalPage(){ return "member/withdrawal";}

    @PostMapping("/member/withdrawal")
    public String withdrawal(Principal principal, HttpServletResponse response){
        memberService.deleteMember(principal.getName());
        Cookie cookie = new Cookie(JwtAuthenticationFilter.AUTHORIZATION_HEADER, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }

    //멤버 프로필사진 url
    @GetMapping(value = "/image/{memberImg}")
    public ResponseEntity<Resource> downloadImageV2(@PathVariable String memberImg) throws IOException {
        String fullPath = fileService.getFullPath(memberImg);
        MediaType mediaType = MediaType.parseMediaType(Files.probeContentType(Paths.get(fullPath)));
        UrlResource resource = new UrlResource("file:" + fullPath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, mediaType.toString())
                .body(resource);
    }

    //내정보 - 글목록
    @GetMapping("/member/writingList")
    public String writingList(){return "member/memberWritingList";}

}
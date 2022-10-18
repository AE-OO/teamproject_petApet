package com.teamproject.petapet.web.member;

import com.teamproject.petapet.web.member.dto.TokenInfo;
import com.teamproject.petapet.web.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberServiceImpl memberServiceImpl;

    @PostMapping("/login")
    public TokenInfo login(@RequestParam String memberId, @RequestParam String memberPw) {
        TokenInfo tokenInfo = memberServiceImpl.login(memberId, memberPw);
        System.out.println(tokenInfo.toString());
        return tokenInfo;
    }
}
package com.teamproject.petapet.web.member.service;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.teamproject.petapet.jwt.JwtTokenProvider;
import com.teamproject.petapet.web.member.dto.TokenInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public List<Member> getMemberList() {
        return memberRepository.findAll(Sort.by(Sort.Direction.DESC, "memberReport"));
    }

    @Override
    public void deleteMember(String memberId) {
        memberRepository.deleteById(memberId);
    }

    @Override
    public void addMemberReport(String memberId) {
        memberRepository.addMemberReport(memberId);
    }

    @Override
    public void updateMemberStopDate(String memberId) {
        memberRepository.updateMemberStopDate(memberId);
    }

    @Override
    public int[] getGenderList() {
//        ArrayList<Integer> genderList = new ArrayList<>();
        int[] genderList = new int[3];

        for(String gender : memberRepository.getGenderList()){
            if(gender.equals("남자")){
                genderList[0] += 1;
            } else if(gender.equals("여자")){
                genderList[1] += 1;
            }else{
                genderList[2] += 1;
            }
        }
        return genderList;
    }


    @Transactional
    public TokenInfo login(String memberId, String memberPw) {
        System.out.println("2)"+memberId+", "+memberPw);
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, memberPw);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }
}

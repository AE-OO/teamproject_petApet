package com.teamproject.petapet.web.member.service;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.member.MemberRepository;
import com.teamproject.petapet.jwt.JwtTokenProvider;

import com.teamproject.petapet.web.member.dto.LoginDto;
import com.teamproject.petapet.web.member.dto.MemberDto;

import com.teamproject.petapet.web.member.dto.MemberResponse;
import com.teamproject.petapet.web.member.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 박채원 22.10.09 작성
 * 장사론 22.19.19 추가
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
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

    @Override
    public Optional<Member> findOne(String memberId) {
        return memberRepository.findByMemberId(memberId);
    }


    @Transactional
    @Override
    public TokenDto login(LoginDto loginDto) {
        // 1. memberId, memberPw 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getMemberId(), loginDto.getMemberPw());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성 후 발급
        return jwtTokenProvider.createToken(authentication);
    }

    @Transactional
    @Override
    public MemberResponse join(MemberDto memberDto) {
        Member member = MemberDto.toEntity(memberDto,passwordEncoder);
        return MemberResponse.of(memberRepository.save(member));
    }

    @Override
    public boolean duplicateCheckMemberId(String memberId) {
        return memberRepository.existsById(memberId);
    }


}

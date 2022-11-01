package com.teamproject.petapet.web.member.service;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.member.MemberRepository;
import com.teamproject.petapet.jwt.JwtTokenProvider;

import com.teamproject.petapet.validatiion.MemberPwEquals;
import com.teamproject.petapet.web.member.dto.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 박채원 22.10.09 작성
 * 장사론 22.10.19 추가 - 로그인, 회원가입
 * 장사론 22.10.27 추가 - 유효성 검사
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

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
    public List<Integer> getAgeList() {
//        List<Integer> ageList = new ArrayList<Integer>();
//        for(int i = 0; i < 6; i++){
//            ageList.add(memberRepository.getAgeList().get(i));
//        }
//        ageList.add(memberRepository.getAgeList().get(6)+memberRepository.getAgeList().get(7)+memberRepository.getAgeList().get(8)+memberRepository.getAgeList().get(9));
        return memberRepository.getAgeList();
    }

    @Override
    public Member findOne(String memberId) {
        return memberRepository.findById(memberId).get();
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

    @Override
    @Transactional
    public void join(JoinDto joinDto) {memberRepository.save(JoinDto.toEntity(joinDto, passwordEncoder));}

    @Transactional(readOnly = true)
    @Override
    public boolean duplicateCheckMemberId(String memberId) {
        return memberRepository.existsById(memberId);
    }


    //유효성 검사
    @Override
    public Map<String, String> validateHandling(BindingResult bindingResult) {
        Map<String, String> validatorResult = new HashMap<>();
        //필드 에러
        for (FieldError error : bindingResult.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        //글로벌 에러
        for (ObjectError error : bindingResult.getGlobalErrors()) {
            if (error.getCode().equals(MemberPwEquals.class.getSimpleName())) {
                String validKeyName = String.format("valid_%s", error.getObjectName());
                validatorResult.put(validKeyName, error.getDefaultMessage());
            }
        }
        return validatorResult;
    }

    @Transactional(readOnly = true)
    @Override
    public MemberDto memberInfo(String memberId) {return MemberDto.fromEntity(memberRepository.findById(memberId).get());}

    @Override
    public boolean checkMemberPw(String memberId, String memberPw) {
        String dbMemberPw = memberRepository.findMemberPw(memberId);
        if(dbMemberPw == null || !passwordEncoder.matches(memberPw,dbMemberPw)){
            return false;
        }
        return true;
    }

}

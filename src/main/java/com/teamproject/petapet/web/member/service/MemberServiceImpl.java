package com.teamproject.petapet.web.member.service;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.member.MemberRepository;
import com.teamproject.petapet.jwt.JwtTokenProvider;

import com.teamproject.petapet.web.member.validatiion.PasswordEquals;
import com.teamproject.petapet.web.member.dto.*;

import com.teamproject.petapet.web.product.fileupload.FileService;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 박채원 22.10.09 작성
 * 장사론 22.10.19 추가 - 로그인, 회원가입
 * 장사론 22.10.27 추가 - 유효성 검사
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public List<MemberDTO> getMemberList() {
        List<Member> memberList = memberRepository.findMemberExceptAdmin();
        return memberList.stream().map(list -> MemberDTO.fromEntityForMemberListOfAdminPage(list)).collect(Collectors.toList());
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
        int[] genderList = new int[3];

        for (String gender : memberRepository.getGenderList()) {
            if (gender.equals("남자")) {
                genderList[0] += 1;
            } else if (gender.equals("여자")) {
                genderList[1] += 1;
            } else {
                genderList[2] += 1;
            }
        }
        return genderList;
    }

    @Override
    public List<Integer> getAgeList() {
        return memberRepository.getAgeList();
    }

    @Override
    public Member findOne(String memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    @Transactional
    @Override
    public TokenDTO login(MemberRequestDTO.LoginDTO loginDTO) {
        // 1. memberId, memberPw 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getMemberId(), loginDTO.getMemberPw());
        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomCompanyUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성 후 발급
        return jwtTokenProvider.createToken(authentication);
    }

    @Transactional
    @Override
    public void join(MemberRequestDTO.JoinDTO joinDTO) {memberRepository.save(joinDTO.toEntity(passwordEncoder));}

    @Transactional(readOnly = true)
    @Override
    public boolean duplicateCheckMemberId(String memberId) {return memberRepository.existsById(memberId);}

    @Override
    public boolean duplicateCheckMemberEmail(String memberEmail) {
        return memberRepository.existsByMemberEmail(memberEmail);
    }

    @Override
    public boolean duplicateCheckMemberPhoneNum(String memberPhoneNum) {
        return memberRepository.existsByMemberPhoneNum(memberPhoneNum);
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
            if (error.getCode().equals(PasswordEquals.class.getSimpleName())) {
                String validKeyName = String.format("valid_%s", error.getObjectName());
                validatorResult.put(validKeyName, error.getDefaultMessage());
            }
        }
        return validatorResult;
    }

    @Transactional(readOnly = true)
    @Override
    public MemberDTO memberInfo(String memberId) {
        return MemberDTO.fromEntity(memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException(memberId + " -> 데이터베이스에서 찾을 수 없습니다.")));
    }

    @Override
    public boolean checkMemberPw(String memberId, String memberPw) {
        return passwordEncoder.matches(memberPw, memberRepository.findById(memberId).get().getMemberPw());
    }

    @Transactional
    @Override
    public void updateMemberInfo(String memberId, MemberRequestDTO.UpdateMemberInfo updateMemberInfo){
        Member member = updateMemberInfo.toEntity();
        memberRepository.updateMember(memberId, member.getMemberBirthday(),member.getMemberPhoneNum(),
                member.getMemberGender(),member.getMemberAddress(),member.getMemberEmail());
    }

    @Override
    public int updateMemberPw(String memberId, String memberPw) {
       return memberRepository.updateMemberPw(memberId,passwordEncoder.encode(memberPw));
    }

    @Override
    public String findMemberId(MemberRequestDTO.FindMemberIdDTO findMemberIdDTO) {
        return MemberDTO.builder()
                .memberId(memberRepository.findMemberId(findMemberIdDTO.getMemberName(),findMemberIdDTO.getMemberPhoneNum()).orElse("0"))
                .build().getMemberId();
    }

    @Override
    public String findMemberPw(MemberRequestDTO.FindMemberPwDTO findMemberPwDTO) {
        return MemberDTO.builder()
                .memberId(memberRepository.existMemberId(
                        findMemberPwDTO.getMemberId(),
                        findMemberPwDTO.getMemberName(),
                        findMemberPwDTO.getMemberPhoneNum())
                        .orElse("0"))
                .build().getMemberId();
    }

    @Override
    public String findEmail(String memberId) {return memberRepository.findById(memberId).get().getMemberEmail();}

    @Override
    public String getOriginalMemberImg(String memberId) {
        return memberRepository.findById(memberId).get().getMemberImg();
    }

    @Override
    public void updateMemberImg(String memberId, String memberImg) {
        Member member = Member.builder().memberId(memberId).memberImg(memberImg).build();
        memberRepository.updateMemberImg(member.getMemberId(),member.getMemberImg());
    }

    @Override
    public void deleteMemberImg(String memberId) {memberRepository.deleteMemberImg(memberId);}

    @Override
    public CommunityMemberDTO memberProfile(String memberId) {
        return CommunityMemberDTO.fromEntity(memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException(memberId + " -> 데이터베이스에서 찾을 수 없습니다.")));
    }


}



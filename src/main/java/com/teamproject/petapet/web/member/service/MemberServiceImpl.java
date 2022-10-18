package com.teamproject.petapet.web.member.service;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

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
}

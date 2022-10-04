package com.teamproject.petapet.domain.member;

import com.teamproject.petapet.domain.community.CommunityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.stream.IntStream;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    CommunityRepository communityRepository;

    @Test
    public void insertMemberDummies(){
        IntStream.rangeClosed(1, 30).forEach(i -> {
           Member member = Member.builder()
                   .memberId("memberId" + i)
                   .memberPw("memberPw" + i)
                   .memberBirthday(Date.valueOf("2022-10-01"))
                   .memberName("member" + i)
                   .memberPhoneNum("010-0000-0000")
                   .memberAddress("서울 강남구")
                   .build();

           memberRepository.save(member);
        });
    }

    @Test
    public void deleteMemberCascadeCommunity(){
        String memberId = "memberId1";
        memberRepository.deleteById(memberId);
    }

    @Test
    public void deleteMemberCascadeAll(){
        String memberId = "memberId2";
        memberRepository.deleteById(memberId);
    }

    @Test
    public void selectMember(){
        System.out.println(memberRepository.findById("memberId3"));
    }
}
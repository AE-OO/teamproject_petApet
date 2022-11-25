package com.teamproject.petapet.domain.member;

import com.teamproject.petapet.domain.community.CommunityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.stream.IntStream;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    CommunityRepository communityRepository;
    PasswordEncoder passwordEncoder;


    @Test
    public void insertMemberDummies(){
        IntStream.rangeClosed(119, 131).forEach(i -> {
           Member member = Member.builder()
                   .memberId("memberId" + i)
                   .memberPw("memberPw" + i)
                   .memberBirthday(Date.valueOf("1950-10-01"))
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
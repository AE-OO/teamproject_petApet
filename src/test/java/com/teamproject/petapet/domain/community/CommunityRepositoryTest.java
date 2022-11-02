package com.teamproject.petapet.domain.community;

import com.teamproject.petapet.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommunityRepositoryTest {

    @Autowired
    CommunityRepository communityRepository;

    @Test
    public void insertCommunityDummies(){
        IntStream.rangeClosed(1,200).forEach(i -> {
            Member member = Member.builder()
                    .memberId("memberId" + i)
                    .memberPw("memberPw" + i)
                    .memberName("memberName" + i)
                    .memberPhoneNum("memberPhoneNum" + i)
                    .build();

            Community community = Community.builder()
                    .communityTitle("communityTitle " + i)
                    .member(member)
                    .communityContent("communityContent " + i)
                    .communityCreatedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd")))
                    .build();

            communityRepository.save(community);
        });
    }
}
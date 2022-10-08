package com.teamproject.petapet.domain.community;

import com.teamproject.petapet.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommunityRepositoryTest {

    @Autowired
    CommunityRepository communityRepository;

    @Test
    public void insertCommunityDummies(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .memberId("memberId" + (i % 30 + 1))
                    .build();

            Community community = Community.builder()
                    .communityTitle("communityTitle " + i)
                    .member(member)
                    .communityContent("communityContent " + i)
                    .communityCategory("커뮤니티")
                    .build();

            communityRepository.save(community);
        });
    }
}
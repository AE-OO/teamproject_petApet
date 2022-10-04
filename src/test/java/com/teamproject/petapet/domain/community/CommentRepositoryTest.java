package com.teamproject.petapet.domain.community;

import com.teamproject.petapet.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void insertCommentDummies(){
        IntStream.rangeClosed(1,300).forEach(i ->{
            long communityId = (long)(Math.random() * 100) + 1;
            Community community = Community.builder().communityId(communityId).build();

            Member member = Member.builder().memberId("memberId" + (i % 30 + 1)).build();

            Comment comment = Comment.builder()
                    .commentContent("comment " + i)
                    .member(member)
                    .community(community)
                    .build();

            commentRepository.save(comment);
        });
    }

}
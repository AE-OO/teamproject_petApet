package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.domain.community.Comment;
import com.teamproject.petapet.domain.community.CommentRepository;
import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.community.CommunityRepository;
import com.teamproject.petapet.domain.community.dto.CommentRequestDto;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final CommunityRepository communityRepository;

    /* CREATE */
    @Transactional
    public Long commentSave(String memberId, Long communityId, CommentRequestDto dto) {
        Member member = memberRepository.findById(memberId).get();
        Community community = communityRepository.findById(communityId).orElseThrow((() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + memberId)));

        dto.setMember(member);
        dto.setCommunity(community);

        Comment comment = dto.toEntity();
        commentRepository.save(comment);

        return dto.getCommentId();
    }
}

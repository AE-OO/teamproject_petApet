package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.domain.community.Comment;
import com.teamproject.petapet.domain.community.CommentRepository;
import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.community.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;

    public Page<Community> list(int page) {
        Sort sort = Sort.by("communityId").descending();
        Pageable pageable = PageRequest.of(page-1, 10, sort);

        return communityRepository.findAll(pageable);
    }

    public Long total() {
        return communityRepository.count();
    }

    public Community view(Long cId) {
        return communityRepository.findById(cId).get();
    }

    public List<Comment> viewComment(Long cId) {
        return commentRepository.findByCommunity_CommunityId(cId);
    }

    public Long totalComment(Long cId) {
        return commentRepository.countByCommunity_CommunityId(cId);
    }
}

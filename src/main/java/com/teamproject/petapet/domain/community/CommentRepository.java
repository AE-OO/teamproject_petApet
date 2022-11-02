package com.teamproject.petapet.domain.community;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCommunity_CommunityId(Long cId);

    Long countByCommunity_CommunityId(Long cId);
}

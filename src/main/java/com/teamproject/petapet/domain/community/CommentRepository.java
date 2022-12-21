package com.teamproject.petapet.domain.community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.community.communityId=:communityId")
    Page<Comment> getCommentList(Long communityId, Pageable pageable);
}

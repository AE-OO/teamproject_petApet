package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.domain.community.Comment;
import com.teamproject.petapet.domain.community.CommentRepository;
import com.teamproject.petapet.web.community.commentDto.CommentDTO;
import com.teamproject.petapet.web.community.commentDto.CommentInsertDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void insertComment(String memberId, CommentInsertDTO commentInsertDTO) {
        commentRepository.save(commentInsertDTO.toEntity(memberId));
    }

    @Override
    public Page<CommentDTO> getCommentPageList(Long communityId, int pageNum) {
        Pageable pageable = PageRequest.of( pageNum, 20);
        return commentRepository.getCommentList(communityId,pageable).map(c -> CommentDTO.fromEntity(c,dateFormat(c)));
    }

    public String dateFormat(Comment comment){
        if (comment.getModifiedDate().toLocalDate().isBefore(LocalDate.now())) {
            return comment.getModifiedDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        } else {
            return comment.getModifiedDate().format(DateTimeFormatter.ofPattern("HH:mm"));
        }
    }
}

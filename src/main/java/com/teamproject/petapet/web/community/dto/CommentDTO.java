package com.teamproject.petapet.web.community.dto;

import com.teamproject.petapet.domain.community.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class CommentDTO {

    private Long commentId;
    private String commentContent;
    private String communityTitle;
    private String modifiedDate;
    private String commentImg;
    private String commentSecret;
    private int isDeleted;
    private Long replyId;
    private String memberId;
    private String memberImg;

    private int commentListSize;
    private Long communityId;

    public static CommentDTO fromEntity(final Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .commentContent(comment.getCommentContent())
                .modifiedDate(comment.getModifiedDate().toLocalDate().isBefore(LocalDate.now())?
                        DateTimeFormatter.ofPattern("yyyy.MM.dd").format(comment.getModifiedDate()):
                        DateTimeFormatter.ofPattern("HH:mm").format(comment.getModifiedDate()))
                .commentImg(comment.getCommentImg()==null?"0":comment.getCommentImg())
                .commentSecret(comment.getCommentSecret())
                .isDeleted(comment.getDepth())
                .replyId(comment.getReplyId())
                .memberId(comment.getMember().getMemberId())
                .memberImg(comment.getMember().getMemberImg()==null?"0":comment.getMember().getMemberImg())
                .build();
    }

    public static CommentDTO fromEntityForWritingList(final Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .commentContent(comment.getCommentContent())
                .modifiedDate(comment.getModifiedDate().toLocalDate().isBefore(LocalDate.now())?
                        DateTimeFormatter.ofPattern("yyyy.MM.dd").format(comment.getModifiedDate()):
                        DateTimeFormatter.ofPattern("HH:mm").format(comment.getModifiedDate()))
                .commentImg(comment.getCommentImg()==null?"0":comment.getCommentImg())
                .commentSecret(comment.getCommentSecret())
                .memberId(comment.getMember().getMemberId())
                .communityTitle(comment.getCommunity().getCommunityTitle())
                .commentListSize(comment.getCommunity().getComment().size())
                .communityId(comment.getCommunity().getCommunityId())
                .build();
    }
}

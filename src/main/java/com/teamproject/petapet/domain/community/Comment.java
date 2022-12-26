package com.teamproject.petapet.domain.community;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.community.converter.EmptyStringToNullConverter;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 박채원 22.10.02 작성
 */

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"member", "community"})
@EntityListeners(value = {AuditingEntityListener.class})
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(columnDefinition = "text not null")
    private String commentContent;

    //비밀댓글
    @Column(length = 1)
    @ColumnDefault("N")
    @Convert(converter = EmptyStringToNullConverter.class)
    private String commentSecret;

    //댓글 이미지
    @Column
    private String commentImg;

    //대댓글
    @Column
    private Long replyId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "communityId")
    private Community community;
}

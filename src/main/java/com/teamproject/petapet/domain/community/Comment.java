package com.teamproject.petapet.domain.community;

import com.teamproject.petapet.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(columnDefinition = "text not null")
    private String commentContent;

//    @CreationTimestamp
//    @Column(updatable = false)
//    private LocalDateTime commentDate;

    @CreatedDate
    @Column
    private String commentCreatedDate;

//    @LastModifiedDate
//    @Column
//    private String commentModifiedDate;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "communityId")
    private Community community;
}

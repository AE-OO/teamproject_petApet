package com.teamproject.petapet.domain.community;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.report.Report;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 박채원 22.10.01 작성
 * 박채원 22.10.09 수정 - 카테고리 컬럼 추가
 * 박채원 22.10.16 수정 - report 테이블과의 연관관계 삭제
 */

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "member")
@DynamicInsert
@EntityListeners(value = {AuditingEntityListener.class})
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;

    @Column(length = 45, nullable = false)
    private String communityTitle;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String communityContent;

    @Column(length = 45, nullable = false)
    private String communityCategory;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime communityDate;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] communityImg;

    @Column(columnDefinition = "bigint(3) default 0")
    private Long communityReport;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    private List<Comment> comment;

}

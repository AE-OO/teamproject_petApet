package com.teamproject.petapet.domain.community;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.community.converter.EmptyStringToNullConverter;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
public class Community extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;

    @Column(length = 75, nullable = false)
    private String communityTitle;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String communityContent;

    @Column(length = 45, nullable = false)
    private String communityCategory;

    @Column(length = 20)
    @Convert(converter = EmptyStringToNullConverter.class)
    private String communitySubCategory;

    @Column(columnDefinition = "bigint(3) default 0")
    private Long communityReport;

    @Column(columnDefinition = "int default 0", nullable = false)
    private int viewCount;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    private List<Comment> comment;

    //커뮤니티 게시물 수정
    public void update(Community community){
        this.communityTitle = community.getCommunityTitle();
        this.communityContent = community.getCommunityContent();
        this.communityCategory = community.getCommunityCategory();
        this.communitySubCategory = community.getCommunitySubCategory();
    }

}

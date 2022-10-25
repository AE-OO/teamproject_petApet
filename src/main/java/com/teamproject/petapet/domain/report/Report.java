package com.teamproject.petapet.domain.report;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 박채원 22.10.02 작성
 * 박채원 22.10.16 수정 - reportCategory 컬럼 삭제, 처리여부를 판단하는 responseStatus 컬럼 추가
 */

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@DynamicInsert
@ToString(exclude = {"member", "community"})
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    
    @Column(columnDefinition = "text not null")
    private String reportReason;

    @Column(columnDefinition = "boolean default 0", nullable = false)
    private boolean responseStatus;

    //둘 다 신고받는 대상
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "communityId")
    private Community community;
}

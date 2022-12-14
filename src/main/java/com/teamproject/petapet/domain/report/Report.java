package com.teamproject.petapet.domain.report;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

/**
 * 박채원 22.10.02 작성
 * 박채원 22.10.16 수정 - reportCategory 컬럼 삭제, 처리여부를 판단하는 responseStatus 컬럼 추가
 * 박채원 22.11.06 수정 - 자세한 이유를 받는 reportReasonDetail 컬럼 추가
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

    @Column(length = 255)
    private String reportReasonDetail;

    @Column(columnDefinition = "boolean default 0", nullable = false)
    private boolean responseStatus;

    //셋 다 신고받는 대상
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "communityId")
    private Community community;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}

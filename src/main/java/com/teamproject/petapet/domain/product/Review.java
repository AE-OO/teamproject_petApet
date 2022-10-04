package com.teamproject.petapet.domain.product;

import com.teamproject.petapet.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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
@ToString(exclude = {"member", "product"})
@EntityListeners(value = {AuditingEntityListener.class})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(length = 45, nullable = false)
    private String reviewTitle;

    @Column(columnDefinition = "text not null")
    private String reviewContent;

    @CreationTimestamp
    @Column
    private LocalDateTime reviewDate;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] reviewImg;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}

package com.teamproject.petapet.domain.dibs;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import lombok.*;

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
public class DibsCommunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dibsCommunityId;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @OneToOne
    @JoinColumn(name = "communityId", nullable = false)
    private Community community;
}

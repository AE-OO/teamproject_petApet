package com.teamproject.petapet.domain.buy;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
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
public class Buy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buyId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime buyDate;

    @Column(length = 45, nullable = false)
    private String buyAddress;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}

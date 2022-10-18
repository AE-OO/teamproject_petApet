package com.teamproject.petapet.domain.product;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

/**
 * 박채원 22.10.01 작성
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "product")
@DynamicInsert
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long counterId;

    @Column(columnDefinition = "bigint(3) default 0")
    private Long counterView;

    @Column(columnDefinition = "bigint(3) default 0")
    private Long counterSell;

    @OneToOne(optional = false)
    @JoinColumn(name = "productId")
    private Product product;

}

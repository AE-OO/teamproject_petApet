package com.teamproject.petapet.domain.member;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
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
@DynamicInsert
@ToString(exclude = {"member"})
@EntityListeners(value = {AuditingEntityListener.class})
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(length = 45, nullable = false)
    private String messageReceiver;

    @Column(length = 45, nullable = false)
    private String messageTitle;

    @Column(length = 45, nullable = false)
    private String messageContent;

    @CreationTimestamp
    @Column
    private LocalDateTime messageDate;

    @Column(columnDefinition = "boolean default false")
    private boolean messageCheck;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;
}

package com.teamproject.petapet.domain.inquired;

import com.teamproject.petapet.domain.member.Member;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;

/**
 * 박채원 22.10.02 작성
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"member"})
@EntityListeners(value = {AuditingEntityListener.class})
public class Inquired {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiredId;

    @Column(length = 45, nullable = false)
    private String inquiredTitle;

    @Column(columnDefinition = "text not null")
    private String inquiredContent;

    @Column
    private Date inquiredDate;


    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] inquiredImg;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;
}

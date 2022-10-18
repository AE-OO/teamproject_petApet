package com.teamproject.petapet.domain.member;

import com.teamproject.petapet.domain.buy.Buy;
import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.community.Comment;
import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.dibs.DibsCommunity;
import com.teamproject.petapet.domain.dibs.DibsProduct;
import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.domain.product.Review;
import com.teamproject.petapet.domain.report.Report;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 박채원 22.10.01 작성
 * 박채원 22.10.09 수정 - 가입날짜 컬럼 추가
 * 박채원 22.10.16 수정 - 정지날짜 컬럼 추가
 */

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"community", "comment"})
@DynamicInsert   //컬럼들에 default값을 주기 위해 사용
@EntityListeners(value = {AuditingEntityListener.class})
public class Member {

    @Id
    private String memberId;

    @Column(length = 45, nullable = false)
    private String memberPw;

    @Column
    private Date memberBirthday;

    @Column(length = 45)
    private String memberAddress;

    @Column(length = 45, nullable = false)
    private String memberPhoneNum;

    @Column(length = 45, nullable = false)
    private String memberName;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime memberJoinDate;

    @Column(columnDefinition = "varchar(10) default '응답안함'")
    private String memberGender;

    @Column(columnDefinition = "bigint(3) default 0")
    private Long memberReport;

    @Column
    private Date memberStopDate;

    //멤버가 어떤 글을 썼는지도 알아야하기 때문에 양방향으로 작성함
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Community> community;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Comment> comment;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Review> review;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Message> message;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Inquired> inquired;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Report> report;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Cart> cart;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Buy> buy;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<DibsProduct> dibsProduct;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<DibsCommunity> dibsCommunity;
}

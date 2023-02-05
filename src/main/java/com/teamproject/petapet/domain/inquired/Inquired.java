package com.teamproject.petapet.domain.inquired;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.teamproject.petapet.domain.company.Company;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 박채원 22.10.02 작성
 *
 * 박채원 22.10.09 수정 - 카테고리 컬럼 추가
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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime inquiredDate;

    @Column(length = 45, nullable = false)
    private String inquiredCategory;

    @Column(length = 200)
    private String answer;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

//    // 구매한 상품
//    @JsonBackReference
//    @ManyToOne
//    @JoinColumn(name = "memberId", nullable = false)
//    private Buy buy;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    // 답변 활성화 컬럼
    @Column(nullable = false)
    private boolean checked;

    @ElementCollection
    @CollectionTable(name = "InquiredImg", joinColumns = @JoinColumn(name = "inquiredImgId", referencedColumnName = "inquiredId"))
    private List<UploadFile> inquiredImg;

    public Inquired(String inquiredTitle, String inquiredContent,String inquiredCategory, Member member, Company company, boolean checked) {
        this.inquiredTitle = inquiredTitle;
        this.inquiredContent = inquiredContent;
        this.inquiredCategory = inquiredCategory;
        this.member = member;
        this.company = company;
        this.checked = checked;
//        this.inquiredImg = inquiredImg;
    }

    public Inquired(Long inquiredId, Boolean checked) {
        this.inquiredId = inquiredId;
        this.checked = checked;
    }
}

package com.teamproject.petapet.domain.member;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.teamproject.petapet.domain.company.Company;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

/**
 * 장사론 22.10.19 작성
 */
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authorityId")
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @JsonBackReference
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "companyId")
    private Company company;

    private String role;

    public static Authority ofMember(Member member) {
        return Authority.builder()
                .role("ROLE_MEMBER")
                .member(member)
                .build();
    }

    public static Authority ofCompany(Company company) {
        return Authority.builder()
                .role("ROLE_COMPANY")
                .company(company)
                .build();
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
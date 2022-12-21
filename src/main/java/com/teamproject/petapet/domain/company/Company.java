package com.teamproject.petapet.domain.company;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.teamproject.petapet.domain.member.Authority;
import com.teamproject.petapet.domain.product.Product;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "product")
@Getter
public class Company {

    @Id
    private String companyId;

    @Column(length = 100, nullable = false)
    private String companyPw;

    @Column(length = 45, nullable = false)
    private String companyName;

    @Column(length = 45, nullable = false)
    private String companyEmail;

    @Column(length = 45, nullable = false)
    private String companyNumber;

    @Column(length = 45, nullable = false)
    private String companyPhoneNum;

    @Column(updatable = false)
    private LocalDateTime companyJoinDate;

    //활성화 컬럼
    @Column(nullable = false)
    private boolean activated;

    @JsonManagedReference
    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
    private List<Product> product;

    @OneToMany(mappedBy = "company", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();

    public void addAuthority(Authority authority) {
        authorities.add(authority);
    }

}

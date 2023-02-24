package com.teamproject.petapet.web.Inquired.dto;

import com.teamproject.petapet.domain.company.Company;
import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import lombok.*;

/**
 * 23.01.18 박채원 작성
 */

public class InquiryRequestDTO {

    // 문의사항 답변 등록 DTO
    @Data
    public static class GetAnswerDTO {
        private String answer;
    }

    @Data
    public static class GetCancleDTO {
        private String answer;
        private Long inquiredId;
    }

    // 상품 상세페이지에서 사업자한테 상품관련 문의하기 DTO
    @Data
    @Builder
    public static class RegisterInquiryToCompany {
        private String inquiredTitle;
        private String inquiredContent;
        private String memberId;
        private String companyId;
        private Long productId;

        public Inquired toEntity(){
            Inquired inquired =
                    Inquired.builder()
                            .inquiredCategory("상품문의")
                            .inquiredTitle(inquiredTitle)
                            .inquiredContent(inquiredContent)
                            .member(Member.builder().memberId(memberId).build())
                            .product(Product.builder().productId(productId).build())
                            .company(Company.builder().companyId(companyId).build())
                            .build();

            return inquired;
        }
    }
}

package com.teamproject.petapet.web.company.dto;

import com.teamproject.petapet.domain.company.Company;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyDTO {

    private String companyId;
    private String companyName;
    private String companyEmail;
    private String companyNumber;
    private String companyPhoneNum;

    public static CompanyDTO fromEntity(Company company) {
        return CompanyDTO.builder()
                .companyId(company.getCompanyId())
                .companyName(company.getCompanyName())
                .companyEmail(company.getCompanyEmail())
                .companyNumber(company.getCompanyNumber())
                .companyPhoneNum(company.getCompanyPhoneNum())
                .build();
    }

    /**
     * 22.11.25 박채원 추가
     * admin페이지에 사업체 가입 승인 리스트 출력시 사용
     */
    public static CompanyDTO fromEntityForJoinAccept(Company company){
        return CompanyDTO.builder()
                .companyId(company.getCompanyId())
                .companyName(company.getCompanyName())
                .companyNumber(company.getCompanyNumber())
                .build();
    }
}

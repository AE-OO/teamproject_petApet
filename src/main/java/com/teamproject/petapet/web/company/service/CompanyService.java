package com.teamproject.petapet.web.company.service;

import com.teamproject.petapet.domain.company.Company;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.web.company.dto.CompanyDTO;
import com.teamproject.petapet.web.company.dto.CompanyRequestDTO;
import com.teamproject.petapet.web.member.dto.TokenDTO;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    boolean duplicateCheckCompanyId(String companyId);
    boolean duplicateCheckCompanyEmail(String companyEmail);
    boolean duplicateCheckCompanyPhoneNum(String companyPhoneNum);
    boolean checkCompanyPw(String companyId, String companyPw);
    boolean checkCompanyNumber(String companyNumber);
    void companyJoin(CompanyRequestDTO.JoinDTO joinDTO);
    TokenDTO login(CompanyRequestDTO.LoginDTO loginDTO);
    String findCompanyId (CompanyRequestDTO.FindCompanyIdDTO findCompanyIdDTO);
    String findCompanyPw (CompanyRequestDTO.FindCompanyPwDTO findCompanyPwDTO);
    int updateCompanyPw (String companyId, String companyPw);
    String findEmail(String companyId);
    CompanyDTO companyInfo(String companyId);
    void updateCompanyInfo (String companyId, CompanyRequestDTO.UpdateCompanyInfo updateCompanyInfo);
    void deleteCompany(String companyId);

    //22.11.25 박채원 추가
    List<CompanyDTO> getCompanyList();
    void acceptJoinCompany(String companyId);
    void refuseJoinCompany(String companyId);


    //23.1.4 성현 추가
    Optional<Company> findOne(String id);

    List<Company> findAllCompany();
}

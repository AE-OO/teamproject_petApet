package com.teamproject.petapet.web.company.service;

import com.teamproject.petapet.web.company.dto.CompanyDTO;
import com.teamproject.petapet.web.company.dto.CompanyRequestDTO;
import com.teamproject.petapet.web.member.dto.TokenDTO;

public interface CompanyService {
    boolean duplicateCheckCompanyId(String companyId);
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







}

package com.teamproject.petapet.web.company.service;

import com.teamproject.petapet.domain.company.Company;
import com.teamproject.petapet.domain.company.CompanyRepository;
import com.teamproject.petapet.jwt.JwtTokenProvider;
import com.teamproject.petapet.web.company.dto.CompanyDTO;
import com.teamproject.petapet.web.company.dto.CompanyRequestDTO;
import com.teamproject.petapet.web.member.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean duplicateCheckCompanyId(String companyId) {
        return companyRepository.existsById("*"+companyId);
    }
    @Override
    public boolean checkCompanyPw(String companyId, String companyPw) {
        return passwordEncoder.matches(companyPw, companyRepository.findCompanyPw(companyId));
    }
    @Override
    public boolean checkCompanyNumber(String companyNumber) {return companyRepository.existsByCompanyNumber(companyNumber);}

    @Transactional
    @Override
    public void companyJoin(CompanyRequestDTO.JoinDTO joinDTO) {companyRepository.save(joinDTO.toEntity(passwordEncoder));}

    @Override
    public TokenDTO login(CompanyRequestDTO.LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("*"+loginDTO.getCompanyId(), loginDTO.getCompanyPw());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.createToken(authentication);
    }

    @Override
    public String findCompanyId(CompanyRequestDTO.FindCompanyIdDTO findCompanyIdDTO) {
        return CompanyDTO.builder()
                .companyId(companyRepository.findCompanyId(findCompanyIdDTO.getCompanyName(),findCompanyIdDTO.getCompanyNumber()).orElse("0")
                        .replace("*",""))
                .build().getCompanyId();
    }

    @Override
    public String findCompanyPw(CompanyRequestDTO.FindCompanyPwDTO findCompanyPwDTO) {
        return CompanyDTO.builder()
                .companyId(companyRepository.existFindCompanyId(
                                "*"+findCompanyPwDTO.getCompanyId(),
                                findCompanyPwDTO.getCompanyName(),
                                findCompanyPwDTO.getCompanyNumber())
                        .orElse("0").replace("*",""))
                .build().getCompanyId();
    }

    @Override
    public int updateCompanyPw(String companyId, String companyPw) {
        return companyRepository.updateCompanyPw(companyId,passwordEncoder.encode(companyPw));
    }

    @Override
    public String findEmail(String companyId) {return companyRepository.findEmail("*"+companyId);}

    @Override
    public CompanyDTO companyInfo(String companyId) {
        return CompanyDTO.fromEntity(companyRepository.findById(companyId)
                .orElseThrow(() -> new UsernameNotFoundException(companyId + " -> 데이터베이스에서 찾을 수 없습니다.")));
    }

    @Override
    public void updateCompanyInfo(String companyId, CompanyRequestDTO.UpdateCompanyInfo updateCompanyInfo) {
        Company company = updateCompanyInfo.toEntity();
        companyRepository.updateCompany(companyId, company.getCompanyEmail(),company.getCompanyPhoneNum());
    }

}

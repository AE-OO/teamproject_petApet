package com.teamproject.petapet.web.member.service;

import com.teamproject.petapet.domain.company.Company;
import com.teamproject.petapet.domain.company.CompanyRepository;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String userId) {
        if(userId.startsWith("*")){
            return companyRepository.findOneWithAuthoritiesByCompanyId(userId)
                    .map(company -> createCompany(userId, company))
                    .orElseThrow(() -> new UsernameNotFoundException(userId + "-> 데이터베이스에서 찾을 수 없습니다."));
        }
        return memberRepository.findOneWithAuthoritiesByMemberId(userId)
                .map(member -> createMember(userId, member))
                .orElseThrow(() -> new UsernameNotFoundException(userId + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private UserDetails createMember(String memberId, Member member) {
        if (member.getMemberStopDate() != null) {
            if (member.getMemberStopDate().toLocalDate().plusDays(3).isBefore(LocalDate.now())) {
                memberRepository.updateActivated(memberId);
            } else {
                throw new LockedException((memberId + " -> 정지회원 입니다."));
            }
        }
        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole()))
                .collect(Collectors.toList());
        return new User(member.getMemberId(), member.getMemberPw(), grantedAuthorities);
    }

    private UserDetails createCompany(String companyId, Company Company) {
        if (!Company.isActivated()) {
            log.info("승인되지 않은 사업자회원 입니다.");
            throw new DisabledException(companyId + " -> 승인되지 않은 사업자회원 입니다.");
        }
        List<GrantedAuthority> grantedAuthorities = Company.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole()))
                .collect(Collectors.toList());
        return new User(Company.getCompanyId(), Company.getCompanyPw(), grantedAuthorities);
    }
}


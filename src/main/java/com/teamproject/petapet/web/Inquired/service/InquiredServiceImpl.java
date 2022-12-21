package com.teamproject.petapet.web.Inquired.service;

import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.domain.inquired.InquiredRepository;
import com.teamproject.petapet.web.Inquired.dto.InquiryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 박채원 22.10.09 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class InquiredServiceImpl implements InquiredService {

    private final InquiredRepository inquiredRepository;

    @Override
    public List<Inquired> getMyInquired(String member) {
        return inquiredRepository.findByMemberId(member);
    }

    @Override
    public Inquired inquiredSubmit(Inquired inquired) {
        return inquiredRepository.save(inquired);
    }

    @Override
    public void removeInquiredOne(Long inquiredId) {
        inquiredRepository.deleteById(inquiredId);
    }

//    @Transactional
//    @Override
//    public void setInquiredCheck(Boolean cheked, Long inquiredId) {
//        inquiredRepository.setCheck(cheked,inquiredId);
//    }

    @Override
    public Inquired findOne(Long id) {
        return inquiredRepository.findById(id).get();
    }

    @Override
    public List<InquiryDTO> getOtherInquiries() {
        List<Inquired> inquiredList = inquiredRepository.getOtherInquiries();
        return inquiredList.stream().map(list -> InquiryDTO.fromEntityForOtherInquiry(list)).collect(Collectors.toList());
    }

    @Override
    public List<InquiryDTO> getCompanyPageInquiryList(String companyId) {
        List<Inquired> inquiredList = inquiredRepository.findAllByCompany_CompanyIdOrderByCheckedAscInquiredDate(companyId);
        return inquiredList.stream().map(list -> InquiryDTO.fromEntityForManageInquiry(list)).collect(Collectors.toList());
    }

}

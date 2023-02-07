package com.teamproject.petapet.web.Inquired.service;

import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.domain.inquired.InquiredRepository;
import com.teamproject.petapet.web.Inquired.dto.InquiryDTO;
import com.teamproject.petapet.web.Inquired.dto.InquiryRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void setInquiredCheck(Long inquiredId, String answer) {
        inquiredRepository.setCheck(inquiredId, answer);
    }

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

    @Override
    public void registerProductInquiry(InquiryRequestDTO.RegisterInquiryToCompany inquiryRequestDTO) {
        inquiredRepository.save(inquiryRequestDTO.toEntity());
    }

    @Override
    public Page<InquiryDTO> getProductDetailPageInquiryList(Long productId, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 10, Sort.by("inquiredDate").descending()); // 왜 그룹핑된 상태에서 정렬을 하는지..
        Page<Inquired> inquiryDTOList = inquiredRepository.findAllByProduct_ProductIdOrderByInquiredDate(productId, pageable);
        return inquiryDTOList.map(list -> InquiryDTO.fromEntityForProductDetailPage(list));
    }

}

package com.teamproject.petapet.web.admin;

import com.teamproject.petapet.web.Inquired.dto.InquiryDTO;
import com.teamproject.petapet.web.Inquired.service.InquiredService;
import com.teamproject.petapet.web.community.dto.CommunityDTO;
import com.teamproject.petapet.web.community.service.CommunityService;
import com.teamproject.petapet.web.company.dto.CompanyDTO;
import com.teamproject.petapet.web.company.service.CompanyService;
import com.teamproject.petapet.web.member.dto.MemberDTO;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.service.ProductService;
import com.teamproject.petapet.web.report.dto.ReportDTO;
import com.teamproject.petapet.web.report.dto.ReportTargetDTO;
import com.teamproject.petapet.web.report.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 박채원 22.12.21 작성
 */

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminRestController {
    private final InquiredService inquiredService;
    private final ProductService productService;
    private final CommunityService communityService;
    private final MemberService memberService;
    private final ReportService reportService;
    private final CompanyService companyService;

    //admin 페이지의 모든 리스트 출력 (이하 8개 메소드)
    @GetMapping(value = "/getNoticeList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommunityDTO>> getNoticeList() {
        return new ResponseEntity<>(communityService.getNotice(), HttpStatus.OK);
    }

    @GetMapping(value = "/getOtherInquiryList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InquiryDTO>> getOtherInquiry() {
        return new ResponseEntity<>(inquiredService.getOtherInquiries(), HttpStatus.OK);
    }

    @GetMapping(value = "/getCommunityReportList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReportDTO>> getCommunityReportList() {
        return new ResponseEntity<>(reportService.getReportCommunityList(), HttpStatus.OK);
    }

    @GetMapping(value = "/getMemberReportList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReportDTO>> getMemberReportList() {
        return new ResponseEntity<>(reportService.getReportMemberList(), HttpStatus.OK);
    }

    @GetMapping(value = "/getProductReportList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReportDTO>> getProductReportList() {
        return new ResponseEntity<>(reportService.getReportProductList(), HttpStatus.OK);
    }

    @GetMapping(value = "/getCommunityList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommunityDTO>> getCommunityList() {
        return new ResponseEntity<>(communityService.getCommunityList(), HttpStatus.OK);
    }

    @GetMapping(value = "/getMemberList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MemberDTO>> getMemberList() {
        return new ResponseEntity<>(memberService.getMemberList(), HttpStatus.OK);
    }

    @GetMapping(value = "/getCompanyJoinAcceptList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CompanyDTO>> getCompanyJoinAcceptList() {
        return new ResponseEntity<>(companyService.getCompanyList(), HttpStatus.OK);
    }


    //회원 정지 기능 구현
    @GetMapping("/disabledMember/{memberId}")
    public void disabledMember(@PathVariable("memberId") String memberId) {
        memberService.updateMemberStopDate(memberId);
    }

    //커뮤니티 신고 승인
    @GetMapping("/acceptCommunityReport/{reportId}")
    public void acceptCommunityReport(@PathVariable("reportId") Long reportId, @RequestParam("communityId") Long communityId) {
        communityService.addCommunityReport(communityId);
        reportService.setResponseStatusCommunity(reportId);
    }

    //회원 신고 승인
    @GetMapping("/acceptMemberReport/{reportId}")
    public void acceptMemberReport(@PathVariable("reportId") Long reportId, @RequestParam("memberId") String memberId) {
        memberService.addMemberReport(memberId);
        reportService.setResponseStatusCommunity(reportId);
    }

    //상품 신고 승인
    @GetMapping("/acceptProductReport/{reportId}")
    public void acceptProductReport(@PathVariable("reportId") Long reportId, @RequestParam("productId") Long productId) {
        productService.addProductReport(productId);
        reportService.setResponseStatusCommunity(reportId);
    }


    //admin 페이지 상단 그래프 표시 (이하 2개 메소드)
    //성별 통계
    @GetMapping("/getGenderList")
    public int[] getGenderList() {
        return memberService.getGenderList();
    }

    //나이 통계
    @GetMapping("/getAgeList")
    public List<Integer> getAgeList() {
        return memberService.getAgeList();
    }


    //상품 재고가 0이 되었을 때 판매상태를 바꿈
    @GetMapping("/setOutOfStock")
    public void setOutOfStock(@RequestParam(value = "productIdList[]") List<String> productIdList) {
        productService.updateProductStatusOutOfStock(productIdList);
    }

    //신고 승인
    @GetMapping("/getReportReason/{id}/{type}")
    public HashMap<String, ReportTargetDTO> getReportReason(@PathVariable("id") Long id, @PathVariable("type") String type) {
        HashMap<String, ReportTargetDTO> reportTarget = new HashMap<String, ReportTargetDTO>();
        reportTarget.put("report", reportService.getOneReport(id, type));
        return reportTarget;
    }

    //신고 반려
    @PostMapping("/refuseReport/{reportId}")
    public void refuseReport(@PathVariable("reportId") Long reportId) {
        reportService.refuseReport(reportId);
    }

    //커뮤니티 강제 삭제
    @GetMapping("/deleteCommunity/{communityId}")
    public void deleteCommunity(@PathVariable("communityId") Long communityId){
        communityService.deleteCommunity(communityId);
    }

    //공지사항 삭제
    @GetMapping("/deleteNotice/{noticeId}")
    public void deleteFAQ(@PathVariable("noticeId") Long noticeId){
        communityService.deleteCommunity(noticeId);
    }

    //회원 강제탈퇴 기능 구현
    @GetMapping("/deleteMember/{memberId}")
    public void deleteMember(@PathVariable("memberId") String memberId){
        memberService.deleteMember(memberId);
    }

}

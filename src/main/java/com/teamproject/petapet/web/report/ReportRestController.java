package com.teamproject.petapet.web.report;

import com.teamproject.petapet.web.report.dto.ReportCommunityDTO;
import com.teamproject.petapet.web.report.dto.ReportMemberDTO;
import com.teamproject.petapet.web.report.dto.ReportProductDTO;
import com.teamproject.petapet.web.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 박채원 22.11.06 작성
 */

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportRestController {

    private final ReportService reportService;

    @PostMapping("/addProductReport")
    public ResponseEntity<String> addProductReport(@RequestBody ReportProductDTO reportProductDTO){
        reportService.addProductReport(reportProductDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/addMemberReport")
    public ResponseEntity<String> addMemberReport(@RequestBody ReportMemberDTO reportMemberDTO){
        reportService.addMemberReport(reportMemberDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/addCommunityReport")
    public ResponseEntity<String> addCommunityReport(@RequestBody ReportCommunityDTO reportCommunityDTO){
        reportService.addCommunityReport(reportCommunityDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}

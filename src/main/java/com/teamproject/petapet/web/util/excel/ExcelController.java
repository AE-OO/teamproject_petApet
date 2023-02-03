package com.teamproject.petapet.web.util.excel;

import com.teamproject.petapet.domain.buy.Buy;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.member.MemberRepository;
import com.teamproject.petapet.web.buy.dto.BuyDTO;
import com.teamproject.petapet.web.buy.service.BuyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ExcelController {

    private final MemberRepository memberRepository;

    private final BuyService buyService;

//    @GetMapping("/{idx}/excelBuy")
    public void downloadBuyExcel(HttpServletResponse response ,@PathVariable("idx") Long buyId) throws IOException {

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("주문 영수증");
        int rowNo = 0;

        Row headerRow = sheet.createRow(rowNo++);
        headerRow.createCell(0).setCellValue("주문 번호");
        headerRow.createCell(1).setCellValue("회원 이름");
        headerRow.createCell(2).setCellValue("회원 주문 상품");
        headerRow.createCell(3).setCellValue("회원 주문 수량");

        Buy buy = buyService.findById(buyId);

        Row row = sheet.createRow(rowNo++);
        row.createCell(0).setCellValue(buy.getBuyId());
        row.createCell(1).setCellValue(buy.getMember().getMemberName());
        row.createCell(2).setCellValue(buy.getProduct().getProductName());
        row.createCell(3).setCellValue(buy.getQuantity());

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=buylist.xls");

        workbook.write(response.getOutputStream());
        workbook.close();
    }


    @GetMapping("/downloadListExcel")
    public void downloadMemberExcel(HttpServletResponse response, Principal principal) throws IOException {
        String filename = "판매 목록";
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(filename);
        int rowNo = 0;

        Row headerRow = sheet.createRow(rowNo++);
        headerRow.createCell(0).setCellValue("주문 번호");
        headerRow.createCell(1).setCellValue("상품명");
        headerRow.createCell(2).setCellValue("구매자");
        headerRow.createCell(3).setCellValue("구매 날짜");
        headerRow.createCell(4).setCellValue("구매 상세");
        headerRow.createCell(5).setCellValue("주문 금액");

        List<BuyDTO> companyPageSalesList = buyService.getCompanyPageSalesList(principal.getName());
        for (BuyDTO buyDTO : companyPageSalesList) {
            Row row = sheet.createRow(rowNo++);
            row.createCell(0).setCellValue(buyDTO.getMerchantUID());
            row.createCell(1).setCellValue(buyDTO.getProductName());
            row.createCell(2).setCellValue(buyDTO.getMemberId());
            row.createCell(3).setCellValue(buyDTO.getBuyDate());
            row.createCell(4).setCellValue(buyDTO.getBuyDetail());
            row.createCell(5).setCellValue(buyDTO.getTotalPrice());
        }
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=list.xls");

        workbook.write(response.getOutputStream());
        workbook.close();
    }


    // DuplicateMember DB -> Excel
    @GetMapping("/excel1")
    public ResponseEntity<InputStreamResource> downloadExcel(HttpServletResponse response) throws IOException {

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("회원 목록");
            int rowNo = 0;

            CellStyle headStyle = workbook.createCellStyle();
            headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_BLUE.getIndex());
            headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font font = workbook.createFont();
            font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
            font.setFontHeightInPoints((short) 13);
            headStyle.setFont(font);

            Row headerRow = sheet.createRow(rowNo++);
            headerRow.createCell(0).setCellValue("글 번호");
            headerRow.createCell(1).setCellValue("작성자");
            headerRow.createCell(2).setCellValue("제목");
            headerRow.createCell(3).setCellValue("내용");
            for(int i=0; i<=3; i++){
                headerRow.getCell(i).setCellStyle(headStyle);
            }

            List<Member> list = memberRepository.findAll();
            for (Member member : list) {
                Row row = sheet.createRow(rowNo++);
                row.createCell(0).setCellValue(member.getMemberId());
                row.createCell(1).setCellValue(member.getMemberName());
                row.createCell(2).setCellValue(member.getMemberGender());
                row.createCell(3).setCellValue(member.getMemberBirthday());
            }

            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 3000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 8000);

            File tmpFile = File.createTempFile("TMP~", ".xlsx");
            try (OutputStream fos = new FileOutputStream(tmpFile);) {
                workbook.write(fos);
            }
            InputStream res = new FileInputStream(tmpFile) {
                @Override
                public void close() throws IOException {
                    super.close();
                    if (tmpFile.delete()) {
                        log.info("임시 파일 삭제 완료");
                    }
                }
            };

            return ResponseEntity.ok() //
                    .contentLength(tmpFile.length()) //
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) //
                    .header("Content-Disposition", "attachment;filename=memberlist.xlsx") //
                    .body(new InputStreamResource(res));

        }
    }
}
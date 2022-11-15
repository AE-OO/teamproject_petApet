package com.teamproject.petapet.web.util.excel;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.member.MemberRepository;
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

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ExcelController {

    private final MemberRepository memberRepository;

    @GetMapping("/excel")
    public void downloadMemberExcel(HttpServletResponse response) throws IOException {

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("회원 목록");
        int rowNo = 0;

        Row headerRow = sheet.createRow(rowNo++);
        headerRow.createCell(0).setCellValue("회원 번호");
        headerRow.createCell(1).setCellValue("회원 이름");
        headerRow.createCell(2).setCellValue("회원 성별");
        headerRow.createCell(3).setCellValue("회원 생일");

        List<Member> list = memberRepository.findAll();
        for (Member member : list) {
            Row row = sheet.createRow(rowNo++);
            row.createCell(0).setCellValue(member.getMemberId());
            row.createCell(1).setCellValue(member.getMemberName());
            row.createCell(2).setCellValue(member.getMemberGender());
            row.createCell(3).setCellValue(member.getMemberBirthday());
        }
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=memberlist.xls");

        workbook.write(response.getOutputStream());
        workbook.close();
    }


    // Member DB -> Excel
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
package com.teamproject.petapet.web.community.controller;

import com.teamproject.petapet.web.community.dto.CommunityDTO;
import com.teamproject.petapet.web.community.service.CommunityService;
import com.teamproject.petapet.web.product.fileupload.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityRestController {
    private final CommunityService communityService;
    private final FileService fileService;

    //커뮤니티 메인 게시글 List
    @PostMapping("/getCommunityList")
    public ResponseEntity<Page<CommunityDTO>> communityList(@RequestParam(defaultValue = "all") String communityCategory,
                                                            @RequestParam(defaultValue = "0") int pageNum,
                                                            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok().body(communityService.getCommunityList(pageNum, pageSize, communityCategory));
    }

    //오늘 작성한 게시글 수
    @PostMapping("/todayPosts")
    public ResponseEntity<Long> todayPosts(@RequestParam(defaultValue = "all") String communityCategory) {
        return new ResponseEntity<>(communityService.countTodayCommunity(communityCategory), HttpStatus.OK);
    }

    //게시글 삭제
    @PostMapping("/delete")
    public void commentDelete(@RequestParam Long communityId,
                              @RequestParam(value = "deleteImg[]", required = false) List<String> deleteImg) {
        if (deleteImg != null) {
            deleteImg.forEach(img -> fileService.deleteFile(img));
        }
        communityService.deleteCommunity(communityId);
    }

    //게시글 제목,아이디
    @PostMapping("/getCommunityTitle")
    public ResponseEntity<CommunityDTO> getCommunityTitle(@RequestParam Long communityId) {
        return new ResponseEntity<>(communityService.getCommunityTitle(communityId), HttpStatus.OK);
    }

    //커뮤니티 검색결과 List
    @PostMapping("/searchList")
    public ResponseEntity<Page<CommunityDTO>> getSearchList(@RequestParam String type,
                                                            @RequestParam String searchContent,
                                                            @RequestParam(defaultValue = "0") int pageNum,
                                                            @RequestParam(defaultValue = "20") int pageSize,
                                                            @RequestParam(defaultValue = "communityId") String sort) {
        return new ResponseEntity<>(communityService.getSearchList(type, searchContent, pageNum, pageSize, sort), HttpStatus.OK);
    }

    //회원 작성글 보기
    @PostMapping("/memberWritingList")
    public ResponseEntity<Page<CommunityDTO>> getMemberWritingList(@RequestParam String type,
                                                                   @RequestParam String memberId,
                                                                   @RequestParam(defaultValue = "0") int pageNum,
                                                                   @RequestParam(defaultValue = "20") int pageSize) {
        return new ResponseEntity<>(communityService.getMemberWritingList(type, memberId, pageNum, pageSize), HttpStatus.OK);
    }

    //커뮤니티 메인화면 공지사항 List
    @PostMapping("/getNotice")
    public ResponseEntity<List<CommunityDTO>> getNotice() {
        return new ResponseEntity<>(communityService.getCommunityMainNotice(), HttpStatus.OK);
    }

    //내정보 - 글목록 - 내가 쓴 글 List
    @PostMapping("/loginMemberWritingList")
    public ResponseEntity<Page<CommunityDTO>> getLoginMemberWritingList(Principal principal,
                                                                        @RequestParam(defaultValue = "0") int pageNum,
                                                                        @RequestParam(defaultValue = "20") int pageSize) {
        return new ResponseEntity<>(communityService.getLoginMemberWritingList(principal.getName(), pageNum, pageSize), HttpStatus.OK);
    }

    //내정보 - 글목록 - 내가 쓴 글 삭제
    @PostMapping("/myWritingDelete")
    public void myWritingDelete(@RequestParam(value = "deleteId[]") List<Long> deleteId,
                                @RequestParam(value = "deleteImg[]") List<String> deleteImg) {
        deleteImg.forEach(img -> fileService.deleteFile(img));
        deleteId.forEach(lists -> communityService.deleteCommunity(lists));
    }

    //index 페이지 최신글, 인기글, 공지사항 List
    @GetMapping("/getIndexList")
    public ResponseEntity<Map<String, Object>> getIndexList() {
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("latest", communityService.getCommunityList(0, 5, "all"));
        pageMap.put("popular", communityService.getPopularCommunityList());
        pageMap.put("notice", communityService.getCommunityList(0, 5, "공지사항"));
        return ResponseEntity.ok().body(pageMap);
    }

}

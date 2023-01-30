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



    @PostMapping("/delete")
    public void commentDelete(@RequestParam Long communityId,
                              @RequestParam(value = "deleteImg[]", required = false) List<String> deleteImg) {
        if (deleteImg != null) {
            deleteImg.forEach(img -> fileService.deleteFile(img));
        }
        communityService.deleteCommunity(communityId);
    }

    @PostMapping("/getCommunityTitle")
    public ResponseEntity<CommunityDTO> getCommunityTitle(@RequestParam Long communityId) {
        return new ResponseEntity<>(communityService.getCommunityTitle(communityId), HttpStatus.OK);
    }


    @PostMapping("/searchList")
    public ResponseEntity<Page<CommunityDTO>> getSearchList(@RequestParam String type,
                                                            @RequestParam String searchContent,
                                                            @RequestParam(defaultValue = "0") int pageNum,
                                                            @RequestParam(defaultValue = "20") int pageSize,
                                                            @RequestParam(defaultValue = "communityId") String sort) {
        return new ResponseEntity<>(communityService.getSearchList(type,searchContent,pageNum, pageSize, sort), HttpStatus.OK);
    }

    @PostMapping("/memberWritingList")
    public ResponseEntity<Page<CommunityDTO>> getMemberWritingList(@RequestParam String type,
                                                                   @RequestParam String memberId,
                                                                   @RequestParam(defaultValue = "0") int pageNum,
                                                                   @RequestParam(defaultValue = "20") int pageSize) {
        return new ResponseEntity<>(communityService.getMemberWritingList(type, memberId, pageNum, pageSize), HttpStatus.OK);
    }

    @PostMapping("/getNotice")
    public ResponseEntity<List<CommunityDTO>> getNotice() {
        return new ResponseEntity<>(communityService.getCommunityMainNotice(), HttpStatus.OK);
    }

    @PostMapping("/loginMemberWritingList")
    public ResponseEntity<Page<CommunityDTO>> getLoginMemberWritingList(Principal principal,
                                                                        @RequestParam(defaultValue = "0") int pageNum,
                                                                        @RequestParam(defaultValue = "20") int pageSize) {
        return new ResponseEntity<>(communityService.getLoginMemberWritingList(principal.getName(), pageNum, pageSize), HttpStatus.OK);
    }

    @PostMapping("/myWritingDelete")
    public void myWritingDelete(@RequestParam(value="deleteList[]") List<Long> deleteList,
                                @RequestParam(value="deleteImg[]") List<String> deleteImg) {
//        if(deleteList != null){
//            deleteList.forEach(lists -> communityService.deleteCommunity(lists));
//        }
    }

    @PostMapping("/getPopularList")
    public ResponseEntity<Page<CommunityDTO>> getPopularList() {
        return ResponseEntity.ok().body(communityService.getPopularList());
    }

    @GetMapping("/getIndexList")
    public ResponseEntity<Map<String, Object>> searchResult() {
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("latest",communityService.getCommunityList(0, 5, "all"));
        pageMap.put("popular",communityService.getPopularList());
        pageMap.put("notice",communityService.getCommunityList(0,5,"공지사항"));
        return ResponseEntity.ok().body(pageMap);
    }

}

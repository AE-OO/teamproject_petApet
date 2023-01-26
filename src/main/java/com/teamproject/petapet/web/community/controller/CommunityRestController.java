package com.teamproject.petapet.web.community.controller;

import com.teamproject.petapet.web.community.dto.CommunityDTO;
import com.teamproject.petapet.web.community.dto.CommunityRequestDTO;
import com.teamproject.petapet.web.community.service.CommunityService;
import com.teamproject.petapet.web.product.fileupload.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityRestController {
    private final CommunityService communityService;
    private final FileService fileService;

    @PostMapping("/getCommunityList")
    public ResponseEntity<Page<CommunityDTO>> communityList(@RequestParam(defaultValue = "all") String communityCategory,
                                                            @RequestParam(defaultValue = "0") int pageNum,
                                                            @RequestParam(defaultValue = "20") int pageSize) {
        return new ResponseEntity<>(communityService.getCommunityList(pageNum, pageSize, communityCategory), HttpStatus.OK);
    }

    @PostMapping("/todayPosts")
    public ResponseEntity<Long> todayPosts(@RequestParam(defaultValue = "all") String communityCategory) {
        return new ResponseEntity<>(communityService.countTodayCommunity(communityCategory), HttpStatus.OK);
    }

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

    @PostMapping("/getNotice")
    public ResponseEntity<List<CommunityDTO>> getNotice() {
        return new ResponseEntity<>(communityService.getCommunityMainNotice(), HttpStatus.OK);
    }

    @PostMapping("/searchList/{type}")
    public ResponseEntity<Page<CommunityDTO>> getSearchList(@PathVariable String type,
                                                            @RequestBody(required = false) CommunityRequestDTO.SearchDTO searchDTO) {
        return new ResponseEntity<>(communityService.getSearchList(type, searchDTO), HttpStatus.OK);
    }

    @PostMapping("/memberWritingList/{type}")
    public ResponseEntity<Page<CommunityDTO>> getMemberWritingList(@PathVariable String type,
                                                                   @RequestParam String memberId,
                                                                   @RequestParam(defaultValue = "0") int pageNum,
                                                                   @RequestParam(defaultValue = "20") int pageSize) {
        return new ResponseEntity<>(communityService.getMemberWritingList(type, memberId, pageNum, pageSize), HttpStatus.OK);
    }

    @PostMapping("/loginMemberWritingList")
    public ResponseEntity<Page<CommunityDTO>> getLoginMemberWritingList(Principal principal,
                                                                        @RequestParam(defaultValue = "0") int pageNum,
                                                                        @RequestParam(defaultValue = "20") int pageSize) {
        return new ResponseEntity<>(communityService.getLoginMemberWritingList(principal.getName(), pageNum, pageSize), HttpStatus.OK);
    }

    @PostMapping("/myWritingDelete")
    public void myWritingDelete(@RequestParam(value="deleteList[]") List<Long> deleteList) {
        if(deleteList != null){
            deleteList.forEach(lists -> communityService.deleteCommunity(lists));
        }
    }

}

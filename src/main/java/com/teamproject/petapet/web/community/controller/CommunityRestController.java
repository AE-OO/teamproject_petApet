package com.teamproject.petapet.web.community.controller;

import com.teamproject.petapet.web.community.service.CommunityService;
import com.teamproject.petapet.web.product.fileupload.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityRestController {
    private final CommunityService communityService;
    private final FileService fileService;

    @PostMapping("/delete")
    public void commentDelete(@RequestParam Long communityId,
                              @RequestParam(value = "deleteImg[]",required = false) List<String> deleteImg){
        if(deleteImg != null){
            deleteImg.forEach(img -> fileService.deleteFile(img));
        }
        communityService.deleteCommunity(communityId);
    }
}

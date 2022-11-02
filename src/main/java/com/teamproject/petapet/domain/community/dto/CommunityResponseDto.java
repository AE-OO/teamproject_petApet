package com.teamproject.petapet.domain.community.dto;

import com.teamproject.petapet.domain.community.Community;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/*게시글 정보를 리턴할 응답(Response) 클래스
  Entity 클래스를 생성자 파라미터로 받아 데이터를 Dto로 변환하여 응답
  별도의 전달 객체를 활용해 연관관계를 맺은 엔티티간의 무한참조를 방지*/
@Getter
public class CommunityResponseDto {
    private Long communityId;
    private String communityTitle;
    private String communityContent;
    private String communityCreatedDate;
    private int communityHit;
    private String memberId;
    private List<CommentResponseDto> comments; //List 타입을 DTO 클래스로해서 엔티티간 무한참조를 방지
    private Long communityReport;

    /* Entity -> Dto */
    public CommunityResponseDto(Community community) {
        this.communityId = community.getCommunityId();
        this.communityTitle = community.getCommunityTitle();
        this.communityContent = community.getCommunityContent();
        this.communityCreatedDate = community.getCommunityCreatedDate();
        this.communityHit = community.getCommunityHit();
        this.memberId = community.getMember().getMemberId();
        this.comments = community.getComment().stream()
                .map(CommentResponseDto::new).collect(Collectors.toList());
        this.communityReport = community.getCommunityReport();
    }

}

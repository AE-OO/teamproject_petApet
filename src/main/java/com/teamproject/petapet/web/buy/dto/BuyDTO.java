package com.teamproject.petapet.web.buy.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BuyDTO {

    private Long buyId;
    private LocalDateTime buyDate;
    private String memberId;
    private String productId;
}

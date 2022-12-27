package com.teamproject.petapet.web.buy.dto;

import com.teamproject.petapet.domain.buy.Buy;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class BuyDTO {

    private Long buyId;
    private String buyDate;
    private String memberId;
    private String productId;
    private String productName;
    private String buyDetail;
    private Long totalPrice;

    public static BuyDTO fromEntityForManageSales(Buy buy){
        return BuyDTO.builder()
                .productName(buy.getProduct().getProductName())
                .memberId(buy.getMember().getMemberId())
                .buyDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(buy.getBuyDate()))
                .buyDetail(buy.getProduct().getProductPrice() + " * " + buy.getQuantity())
                .totalPrice(buy.getProduct().getProductPrice()*buy.getQuantity())
                .build();
    }
}

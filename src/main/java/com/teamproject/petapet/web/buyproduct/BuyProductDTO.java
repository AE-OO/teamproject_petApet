package com.teamproject.petapet.web.buyproduct;

import com.teamproject.petapet.domain.buyproduct.BuyProduct;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class BuyProductDTO {

    private Long buyId;
    private String buyDate;
    private String memberId;
    private String productId;
    private String productName;
    private String buyDetail;
    private Long totalPrice;
    private String merchantUID;

    public static BuyProductDTO fromEntityForManageSales(BuyProduct buyProduct){
        return BuyProductDTO.builder()
                .merchantUID(buyProduct.getBuy().getMerchantUid())
                .productName(buyProduct.getProduct().getProductName())
                .memberId(buyProduct.getBuy().getMember().getMemberId())
                .buyDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(buyProduct.getBuy().getBuyDate()))
                .buyDetail(buyProduct.getProduct().getProductPrice() + " * " + buyProduct.getQuantity())
                .totalPrice(buyProduct.getProduct().getProductPrice()*buyProduct.getQuantity())
                .build();
    }
}

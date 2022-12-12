package com.teamproject.petapet.web.dibs.dibsdtos;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DibsProductDTO {

    private Long productId;
    private Member member;
    private Product product;

}

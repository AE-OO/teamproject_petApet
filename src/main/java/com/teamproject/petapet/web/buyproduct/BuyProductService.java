package com.teamproject.petapet.web.buyproduct;

import com.teamproject.petapet.domain.buyproduct.BuyProduct;
import com.teamproject.petapet.web.product.productdtos.ProductDTO;

import java.util.List;

public interface BuyProductService {

    BuyProduct save(BuyProduct buyProduct);

    List<ProductDTO> getCompanyProductList(String companyId);

}

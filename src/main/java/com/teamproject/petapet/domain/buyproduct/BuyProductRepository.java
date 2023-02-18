package com.teamproject.petapet.domain.buyproduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuyProductRepository extends JpaRepository<BuyProduct, Long> {

    List<BuyProduct> getAllByProduct_Company_CompanyIdOrderByBuyDesc(String companyId);

}

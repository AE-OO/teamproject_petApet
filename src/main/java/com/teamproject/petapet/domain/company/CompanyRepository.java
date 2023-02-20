package com.teamproject.petapet.domain.company;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {
    @Query("select c.companyPw from Company c where c.companyId = :companyId")
    String findCompanyPw(@Param("companyId") String companyId);
    @EntityGraph(attributePaths = "authorities")
    Optional<Company> findOneWithAuthoritiesByCompanyId(String companyId);
    Boolean existsByCompanyNumber(String companyNumber);
    @Query("select c.companyId from Company c where c.companyName = :companyName and c.companyNumber = :companyNumber")
    Optional<String> findCompanyId(@Param("companyName") String companyName, String companyNumber);
    @Query("select c.companyId from Company c where c.companyId = :companyId and c.companyName = :companyName and c.companyNumber = :companyNumber")
    Optional<String> existFindCompanyId(@Param("companyId") String companyId, String companyName, String companyNumber);

    @Modifying
    @Transactional
    @Query("update Company c set c.companyPw =:companyPw where c.companyId = :companyId")
    int updateCompanyPw(@Param("companyId") String companyId,@Param("companyPw") String companyPw);

    @Query("select c.companyEmail from Company c where c.companyId = :companyId")
    String findEmail(@Param("companyId") String companyId);

    @Modifying
    @Transactional
    @Query("update Company c set c.companyEmail = :companyEmail, c.companyPhoneNum = :companyPhoneNum where c.companyId = :companyId")
    void updateCompany(@Param("companyId") String companyId,@Param("companyEmail") String companyEmail,@Param("companyPhoneNum") String companyPhoneNum);

    //22.11.25 박채원 추가
    List<Company> getCompaniesByActivatedIsFalse();

    @Modifying
    @Transactional
    @Query("update Company c set c.activated = true, c.companyJoinDate = current_date where c.companyId = :companyId")
    void acceptJoinCompany(@Param("companyId") String companyId);

    boolean existsByCompanyEmail(String CompanyEmail);
    boolean existsByCompanyPhoneNum(String CompanyPhoneNum);

    List<Company> findAll();
}

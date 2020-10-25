package com.sg.loan.repository;

import com.sg.loan.entity.LoanRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerLoanRepository extends JpaRepository<LoanRequestEntity, Long> {

    @Query("SELECT u FROM LoanRequestEntity u WHERE u.loanRefNumber = ?1")
    List<LoanRequestEntity> getCustomerByloanRefNumberr(String kycNumber);


}

package com.hostel.mess.repository;

import com.hostel.mess.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByUserId(Long userId);
    Optional<Bill> findByUserIdAndMonthAndYear(Long userId, int month, int year);
    List<Bill> findByPaidFalse();
    List<Bill> findByUserIdAndPaidFalse(Long userId);
}

package com.hostel.mess.repository;

import com.hostel.mess.entity.Attendance;
import com.hostel.mess.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUserId(Long userId);
    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByUserIdAndDateBetween(Long userId, LocalDate from, LocalDate to);
    Optional<Attendance> findByUserIdAndDateAndMealType(Long userId, LocalDate date, MealType mealType);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.user.id = :userId AND a.present = true AND a.date BETWEEN :from AND :to")
    long countPresentByUserAndDateRange(Long userId, LocalDate from, LocalDate to);
}

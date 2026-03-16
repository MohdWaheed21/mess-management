package com.hostel.mess.repository;

import com.hostel.mess.entity.MenuItem;
import com.hostel.mess.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.DayOfWeek;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByDayOfWeekAndActiveTrue(DayOfWeek day);
    List<MenuItem> findByDayOfWeekAndMealTypeAndActiveTrue(DayOfWeek day, MealType mealType);
    List<MenuItem> findByActiveTrue();
}

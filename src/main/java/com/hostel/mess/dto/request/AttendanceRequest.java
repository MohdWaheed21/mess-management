package com.hostel.mess.dto.request;

import com.hostel.mess.enums.MealType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AttendanceRequest {
    @NotNull
    private Long userId;
    @NotNull
    private LocalDate date;
    @NotNull
    private MealType mealType;
    private boolean present;
}

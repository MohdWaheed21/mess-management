package com.hostel.mess.dto.response;

import com.hostel.mess.enums.MealType;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponse {
    private Long id;
    private Long userId;
    private String userName;
    private LocalDate date;
    private MealType mealType;
    private boolean present;
    private String markedBy;
}

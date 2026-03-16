package com.hostel.mess.dto.response;

import com.hostel.mess.enums.MealType;
import lombok.*;
import java.time.DayOfWeek;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemResponse {
    private Long id;
    private DayOfWeek dayOfWeek;
    private MealType mealType;
    private String itemName;
    private String description;
    private boolean vegetarian;
}

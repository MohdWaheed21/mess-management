package com.hostel.mess.dto.request;

import com.hostel.mess.enums.MealType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.DayOfWeek;

@Data
public class MenuItemRequest {
    @NotNull
    private DayOfWeek dayOfWeek;
    @NotNull
    private MealType mealType;
    @NotBlank
    private String itemName;
    private String description;
    private boolean vegetarian;
}

package com.hostel.mess.entity;

import com.hostel.mess.enums.MealType;
import jakarta.persistence.*;
import lombok.*;
import java.time.DayOfWeek;

@Entity
@Table(name = "menu_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;

    @Column(nullable = false)
    private String itemName;

    private String description;
    private boolean vegetarian;
    private boolean active = true;
}

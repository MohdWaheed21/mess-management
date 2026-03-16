package com.hostel.mess.entity;
 
import com.hostel.mess.enums.MealType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
 
@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
 
    @Column(nullable = false)
    private LocalDate date;
 
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;
 
    private boolean present;
    private String markedBy;
}
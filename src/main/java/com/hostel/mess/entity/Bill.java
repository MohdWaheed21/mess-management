package com.hostel.mess.entity;
 
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
 
@Entity
@Table(name = "bills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
 
    private int month;
    private int year;
 
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private BigDecimal dueAmount;
 
    private boolean paid;
    private LocalDate dueDate;
    private LocalDate paidDate;
 
    private String remarks;
}
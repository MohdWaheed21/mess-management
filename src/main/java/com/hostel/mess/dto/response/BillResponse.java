package com.hostel.mess.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillResponse {
    private Long id;
    private Long userId;
    private String userName;
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

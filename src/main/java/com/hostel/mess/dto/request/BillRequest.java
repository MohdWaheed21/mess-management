package com.hostel.mess.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BillRequest {
    @NotNull
    private Long userId;
    @NotNull
    private int month;
    @NotNull
    private int year;
    @NotNull
    private BigDecimal totalAmount;
    private LocalDate dueDate;
    private String remarks;
}

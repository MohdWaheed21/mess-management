package com.hostel.mess.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterRequest {
    @NotBlank
    private String name;

    @Email @NotBlank
    private String email;

    @NotBlank @Size(min = 6)
    private String password;

    private String rollNumber;
    private String roomNumber;
    private String phone;
    private String hostelBlock;
    private LocalDate joinDate;
}

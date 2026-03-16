package com.hostel.mess.dto.response;

import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String rollNumber;
    private String roomNumber;
    private String phone;
    private String hostelBlock;
    private String role;
    private LocalDate joinDate;
    private boolean active;
}

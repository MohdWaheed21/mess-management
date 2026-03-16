package com.hostel.mess.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String email;
    private String name;
    private String role;
    private Long userId;
}

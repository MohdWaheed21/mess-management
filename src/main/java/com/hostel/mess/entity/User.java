package com.hostel.mess.entity;

import com.hostel.mess.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String rollNumber;

    private String roomNumber;
    private String phone;
    private String hostelBlock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private LocalDate joinDate;

    private boolean active = true;
}

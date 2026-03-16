package com.hostel.mess.service.impl;

import com.hostel.mess.dto.request.LoginRequest;
import com.hostel.mess.dto.request.RegisterRequest;
import com.hostel.mess.dto.response.AuthResponse;
import com.hostel.mess.entity.User;
import com.hostel.mess.enums.Role;
import com.hostel.mess.repository.UserRepository;
import com.hostel.mess.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email already registered");

        User user = User.builder()
            .name(req.getName())
            .email(req.getEmail())
            .password(passwordEncoder.encode(req.getPassword()))
            .rollNumber(req.getRollNumber())
            .roomNumber(req.getRoomNumber())
            .phone(req.getPhone())
            .hostelBlock(req.getHostelBlock())
            .joinDate(req.getJoinDate() != null ? req.getJoinDate() : LocalDate.now())
            .role(Role.ROLE_STUDENT)
            .active(true)
            .build();

        userRepository.save(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return AuthResponse.builder()
            .token(token).email(user.getEmail())
            .name(user.getName()).role(user.getRole().name())
            .userId(user.getId()).build();
    }

    public AuthResponse login(LoginRequest req) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        User user = userRepository.findByEmail(req.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return AuthResponse.builder()
            .token(token).email(user.getEmail())
            .name(user.getName()).role(user.getRole().name())
            .userId(user.getId()).build();
    }
}

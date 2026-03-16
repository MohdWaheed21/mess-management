package com.hostel.mess.service.impl;

import com.hostel.mess.dto.response.UserResponse;
import com.hostel.mess.entity.User;
import com.hostel.mess.enums.Role;
import com.hostel.mess.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllStudents() {
        return userRepository.findByActiveTrue().stream()
            .filter(u -> u.getRole() == Role.ROLE_STUDENT)
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return toResponse(user);
    }

    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(false);
        userRepository.save(user);
    }

    public UserResponse toResponse(User u) {
        return UserResponse.builder()
            .id(u.getId())
            .name(u.getName())
            .email(u.getEmail())
            .rollNumber(u.getRollNumber())
            .roomNumber(u.getRoomNumber())
            .phone(u.getPhone())
            .hostelBlock(u.getHostelBlock())
            .role(u.getRole().name())
            .joinDate(u.getJoinDate())
            .active(u.isActive())
            .build();
    }
}

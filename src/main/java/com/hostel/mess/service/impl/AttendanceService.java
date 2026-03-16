package com.hostel.mess.service.impl;
 
import com.hostel.mess.dto.request.AttendanceRequest;
import com.hostel.mess.dto.response.AttendanceResponse;
import com.hostel.mess.entity.Attendance;
import com.hostel.mess.entity.User;
import com.hostel.mess.repository.AttendanceRepository;
import com.hostel.mess.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
 
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
 
@Service
@RequiredArgsConstructor
public class AttendanceService {
 
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
 
    @Transactional
    public AttendanceResponse markAttendance(AttendanceRequest req, String markedBy) {
        User user = userRepository.findById(req.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
 
        Attendance attendance = attendanceRepository
            .findByUserIdAndDateAndMealType(req.getUserId(), req.getDate(), req.getMealType())
            .orElse(Attendance.builder()
                .user(user)
                .date(req.getDate())
                .mealType(req.getMealType())
                .build());
 
        attendance.setPresent(req.isPresent());
        attendance.setMarkedBy(markedBy);
        return toResponse(attendanceRepository.save(attendance));
    }
 
    @Transactional
    public List<AttendanceResponse> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date)
            .stream().map(this::toResponse).collect(Collectors.toList());
    }
 
    @Transactional
    public List<AttendanceResponse> getStudentAttendance(Long userId, LocalDate from, LocalDate to) {
        return attendanceRepository.findByUserIdAndDateBetween(userId, from, to)
            .stream().map(this::toResponse).collect(Collectors.toList());
    }
 
    public long getMonthlyPresentCount(Long userId, int year, int month) {
        LocalDate from = LocalDate.of(year, month, 1);
        LocalDate to = from.withDayOfMonth(from.lengthOfMonth());
        return attendanceRepository.countPresentByUserAndDateRange(userId, from, to);
    }
 
    private AttendanceResponse toResponse(Attendance a) {
        return AttendanceResponse.builder()
            .id(a.getId())
            .userId(a.getUser().getId())
            .userName(a.getUser().getName())
            .date(a.getDate())
            .mealType(a.getMealType())
            .present(a.isPresent())
            .markedBy(a.getMarkedBy())
            .build();
    }
}
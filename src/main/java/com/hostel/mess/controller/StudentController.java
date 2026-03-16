package com.hostel.mess.controller;

import com.hostel.mess.dto.request.FeedbackRequest;
import com.hostel.mess.dto.response.*;
import com.hostel.mess.repository.UserRepository;
import com.hostel.mess.service.impl.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final MenuService menuService;
    private final AttendanceService attendanceService;
    private final BillService billService;
    private final FeedbackService feedbackService;
    private final UserRepository userRepository;

    private Long getCurrentUserId(Authentication auth) {
        return userRepository.findByEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found")).getId();
    }

    // ─── Menu ──────────────────────────────────────────────────
    @GetMapping("/menu")
    public ResponseEntity<Map<String, List<MenuItemResponse>>> getWeeklyMenu() {
        return ResponseEntity.ok(menuService.getWeeklyMenu());
    }

    @GetMapping("/menu/today")
    public ResponseEntity<List<MenuItemResponse>> getTodayMenu() {
        return ResponseEntity.ok(menuService.getMenuByDay(LocalDate.now().getDayOfWeek()));
    }

    // ─── Attendance ────────────────────────────────────────────
    @GetMapping("/attendance")
    public ResponseEntity<List<AttendanceResponse>> getMyAttendance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            Authentication auth) {
        return ResponseEntity.ok(attendanceService.getStudentAttendance(getCurrentUserId(auth), from, to));
    }

    @GetMapping("/attendance/count")
    public ResponseEntity<Long> getMonthlyCount(@RequestParam int year,
                                                  @RequestParam int month,
                                                  Authentication auth) {
        return ResponseEntity.ok(attendanceService.getMonthlyPresentCount(getCurrentUserId(auth), year, month));
    }

    // ─── Bills ─────────────────────────────────────────────────
    @GetMapping("/bills")
    public ResponseEntity<List<BillResponse>> getMyBills(Authentication auth) {
        return ResponseEntity.ok(billService.getStudentBills(getCurrentUserId(auth)));
    }

    // ─── Feedback ──────────────────────────────────────────────
    @PostMapping("/feedback")
    public ResponseEntity<FeedbackResponse> submitFeedback(@Valid @RequestBody FeedbackRequest req,
                                                            Authentication auth) {
        return ResponseEntity.ok(feedbackService.submitFeedback(req, getCurrentUserId(auth)));
    }

    @GetMapping("/feedback")
    public ResponseEntity<List<FeedbackResponse>> getMyFeedback(Authentication auth) {
        return ResponseEntity.ok(feedbackService.getStudentFeedback(getCurrentUserId(auth)));
    }

    // ─── Profile ───────────────────────────────────────────────
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(Authentication auth) {
        Long id = getCurrentUserId(auth);
        return userRepository.findById(id)
            .map(u -> ResponseEntity.ok(UserResponse.builder()
                .id(u.getId()).name(u.getName()).email(u.getEmail())
                .rollNumber(u.getRollNumber()).roomNumber(u.getRoomNumber())
                .phone(u.getPhone()).hostelBlock(u.getHostelBlock())
                .role(u.getRole().name()).joinDate(u.getJoinDate()).active(u.isActive())
                .build()))
            .orElse(ResponseEntity.notFound().build());
    }
}

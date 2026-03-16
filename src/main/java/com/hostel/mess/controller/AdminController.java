package com.hostel.mess.controller;

import com.hostel.mess.dto.request.*;
import com.hostel.mess.dto.response.*;
import com.hostel.mess.service.impl.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final MenuService menuService;
    private final AttendanceService attendanceService;
    private final BillService billService;
    private final FeedbackService feedbackService;

    // ─── Students ──────────────────────────────────────────────
    @GetMapping("/students")
    public ResponseEntity<List<UserResponse>> getAllStudents() {
        return ResponseEntity.ok(userService.getAllStudents());
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<UserResponse> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deactivateStudent(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }

    // ─── Menu ──────────────────────────────────────────────────
    @PostMapping("/menu")
    public ResponseEntity<MenuItemResponse> addMenu(@Valid @RequestBody MenuItemRequest req) {
        return ResponseEntity.ok(menuService.addMenuItem(req));
    }

    @PutMapping("/menu/{id}")
    public ResponseEntity<MenuItemResponse> updateMenu(@PathVariable Long id,
                                                        @Valid @RequestBody MenuItemRequest req) {
        return ResponseEntity.ok(menuService.updateMenuItem(id, req));
    }

    @DeleteMapping("/menu/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }

    // ─── Attendance ────────────────────────────────────────────
    @PostMapping("/attendance")
    public ResponseEntity<AttendanceResponse> markAttendance(@Valid @RequestBody AttendanceRequest req,
                                                              Authentication auth) {
        return ResponseEntity.ok(attendanceService.markAttendance(req, auth.getName()));
    }

    @GetMapping("/attendance")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByDate(date));
    }

    // ─── Bills ─────────────────────────────────────────────────
    @PostMapping("/bills")
    public ResponseEntity<BillResponse> generateBill(@Valid @RequestBody BillRequest req) {
        return ResponseEntity.ok(billService.generateBill(req));
    }

    @PutMapping("/bills/{id}/pay")
    public ResponseEntity<BillResponse> markPayment(@PathVariable Long id,
                                                     @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(billService.payBill(id, amount));
    }

    @GetMapping("/bills")
    public ResponseEntity<List<BillResponse>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    @GetMapping("/bills/unpaid")
    public ResponseEntity<List<BillResponse>> getUnpaidBills() {
        return ResponseEntity.ok(billService.getUnpaidBills());
    }

    // ─── Feedback ──────────────────────────────────────────────
    @GetMapping("/feedback")
    public ResponseEntity<List<FeedbackResponse>> getAllFeedback() {
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }

    @GetMapping("/feedback/pending")
    public ResponseEntity<List<FeedbackResponse>> getPendingFeedback() {
        return ResponseEntity.ok(feedbackService.getPendingFeedback());
    }

    @PutMapping("/feedback/{id}/reply")
    public ResponseEntity<FeedbackResponse> replyFeedback(@PathVariable Long id,
                                                            @RequestParam String reply) {
        return ResponseEntity.ok(feedbackService.replyToFeedback(id, reply));
    }

    // ─── Weekly Menu ────────────────────────────────────────────
    @GetMapping("/menu/weekly")
    public ResponseEntity<Map<String, List<MenuItemResponse>>> getWeeklyMenu() {
        return ResponseEntity.ok(menuService.getWeeklyMenu());
    }
}

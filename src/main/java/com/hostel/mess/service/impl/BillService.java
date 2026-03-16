package com.hostel.mess.service.impl;
 
import com.hostel.mess.dto.request.BillRequest;
import com.hostel.mess.dto.response.BillResponse;
import com.hostel.mess.entity.Bill;
import com.hostel.mess.entity.User;
import com.hostel.mess.repository.BillRepository;
import com.hostel.mess.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
 
@Service
@RequiredArgsConstructor
public class BillService {
 
    private final BillRepository billRepository;
    private final UserRepository userRepository;
 
    @Transactional
    public BillResponse generateBill(BillRequest req) {
        User user = userRepository.findById(req.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
 
        Bill bill = Bill.builder()
            .user(user)
            .month(req.getMonth())
            .year(req.getYear())
            .totalAmount(req.getTotalAmount())
            .paidAmount(BigDecimal.ZERO)
            .dueAmount(req.getTotalAmount())
            .paid(false)
            .dueDate(req.getDueDate() != null ? req.getDueDate() : LocalDate.now().plusDays(15))
            .remarks(req.getRemarks())
            .build();
 
        return toResponse(billRepository.save(bill));
    }
 
    @Transactional
    public BillResponse payBill(Long billId, BigDecimal amount) {
        Bill bill = billRepository.findById(billId)
            .orElseThrow(() -> new RuntimeException("Bill not found"));
 
        bill.setPaidAmount(bill.getPaidAmount().add(amount));
        bill.setDueAmount(bill.getTotalAmount().subtract(bill.getPaidAmount()));
        if (bill.getDueAmount().compareTo(BigDecimal.ZERO) <= 0) {
            bill.setPaid(true);
            bill.setPaidDate(LocalDate.now());
            bill.setDueAmount(BigDecimal.ZERO);
        }
        return toResponse(billRepository.save(bill));
    }
 
    @Transactional
    public List<BillResponse> getStudentBills(Long userId) {
        return billRepository.findByUserId(userId)
            .stream().map(this::toResponse).collect(Collectors.toList());
    }
 
    @Transactional
    public List<BillResponse> getUnpaidBills() {
        return billRepository.findByPaidFalse()
            .stream().map(this::toResponse).collect(Collectors.toList());
    }
 
    @Transactional
    public List<BillResponse> getAllBills() {
        return billRepository.findAll()
            .stream().map(this::toResponse).collect(Collectors.toList());
    }
 
    private BillResponse toResponse(Bill b) {
        return BillResponse.builder()
            .id(b.getId())
            .userId(b.getUser().getId())
            .userName(b.getUser().getName())
            .month(b.getMonth())
            .year(b.getYear())
            .totalAmount(b.getTotalAmount())
            .paidAmount(b.getPaidAmount())
            .dueAmount(b.getDueAmount())
            .paid(b.isPaid())
            .dueDate(b.getDueDate())
            .paidDate(b.getPaidDate())
            .remarks(b.getRemarks())
            .build();
    }
}
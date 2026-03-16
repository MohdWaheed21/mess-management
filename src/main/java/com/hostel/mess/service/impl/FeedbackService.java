package com.hostel.mess.service.impl;
 
import com.hostel.mess.dto.request.FeedbackRequest;
import com.hostel.mess.dto.response.FeedbackResponse;
import com.hostel.mess.entity.Feedback;
import com.hostel.mess.entity.User;
import com.hostel.mess.enums.FeedbackStatus;
import com.hostel.mess.repository.FeedbackRepository;
import com.hostel.mess.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
@RequiredArgsConstructor
public class FeedbackService {
 
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
 
    @Transactional
    public FeedbackResponse submitFeedback(FeedbackRequest req, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
 
        Feedback feedback = Feedback.builder()
            .user(user)
            .subject(req.getSubject())
            .message(req.getMessage())
            .rating(req.getRating())
            .status(FeedbackStatus.PENDING)
            .build();
 
        return toResponse(feedbackRepository.save(feedback));
    }
 
    @Transactional
    public FeedbackResponse replyToFeedback(Long feedbackId, String reply) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
            .orElseThrow(() -> new RuntimeException("Feedback not found"));
        feedback.setAdminReply(reply);
        feedback.setStatus(FeedbackStatus.RESOLVED);
        return toResponse(feedbackRepository.save(feedback));
    }
 
    @Transactional
    public List<FeedbackResponse> getAllFeedback() {
        return feedbackRepository.findAllByOrderByCreatedAtDesc()
            .stream().map(this::toResponse).collect(Collectors.toList());
    }
 
    @Transactional
    public List<FeedbackResponse> getStudentFeedback(Long userId) {
        return feedbackRepository.findByUserId(userId)
            .stream().map(this::toResponse).collect(Collectors.toList());
    }
 
    @Transactional
    public List<FeedbackResponse> getPendingFeedback() {
        return feedbackRepository.findByStatus(FeedbackStatus.PENDING)
            .stream().map(this::toResponse).collect(Collectors.toList());
    }
 
    private FeedbackResponse toResponse(Feedback f) {
        return FeedbackResponse.builder()
            .id(f.getId())
            .userId(f.getUser().getId())
            .userName(f.getUser().getName())
            .subject(f.getSubject())
            .message(f.getMessage())
            .rating(f.getRating())
            .status(f.getStatus())
            .adminReply(f.getAdminReply())
            .createdAt(f.getCreatedAt())
            .build();
    }
}
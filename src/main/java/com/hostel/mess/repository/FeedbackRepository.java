package com.hostel.mess.repository;

import com.hostel.mess.entity.Feedback;
import com.hostel.mess.enums.FeedbackStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByUserId(Long userId);
    List<Feedback> findByStatus(FeedbackStatus status);
    List<Feedback> findAllByOrderByCreatedAtDesc();
}

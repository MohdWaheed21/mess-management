package com.hostel.mess.entity;
 
import com.hostel.mess.enums.FeedbackStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
 
@Entity
@Table(name = "feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
 
    @Column(nullable = false)
    private String subject;
 
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
 
    private int rating; // 1-5
 
    @Enumerated(EnumType.STRING)
    private FeedbackStatus status = FeedbackStatus.PENDING;
 
    private String adminReply;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
 
    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }
 
    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}
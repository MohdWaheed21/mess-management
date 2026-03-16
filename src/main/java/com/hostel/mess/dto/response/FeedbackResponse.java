package com.hostel.mess.dto.response;

import com.hostel.mess.enums.FeedbackStatus;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String subject;
    private String message;
    private int rating;
    private FeedbackStatus status;
    private String adminReply;
    private LocalDateTime createdAt;
}

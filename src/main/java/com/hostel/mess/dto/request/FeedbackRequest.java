package com.hostel.mess.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FeedbackRequest {
    @NotBlank
    private String subject;
    @NotBlank
    private String message;
    @Min(1) @Max(5)
    private int rating;
}

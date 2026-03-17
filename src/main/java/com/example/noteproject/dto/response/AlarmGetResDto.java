package com.example.noteproject.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class AlarmGetResDto {
    private Long id;
    @NotBlank
    private String alarmName;
    @NotBlank
    private LocalDateTime alarmTime;
}

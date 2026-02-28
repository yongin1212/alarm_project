package com.example.noteproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class AlarmGetResDto {
    private Long id;
    private String alarmName;
    private LocalDateTime alarmTime;
}

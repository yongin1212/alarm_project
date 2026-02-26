package com.example.noteproject.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record AlarmPostReqDto(MultipartFile song, String songName, String alarmName, LocalDateTime alarmTime) {
}

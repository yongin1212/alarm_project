package com.example.noteproject.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlarmPostResDto {
    private boolean success;
    private String message;
}

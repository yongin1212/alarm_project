package com.example.noteproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SignupResDto {

    @NotBlank
    private boolean success;

    @NotBlank
    private String message;
}

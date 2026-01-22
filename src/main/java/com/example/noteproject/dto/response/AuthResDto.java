package com.example.noteproject.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AuthResDto {

    @NotBlank
    private boolean success;

    @NotBlank
    private String message;
}

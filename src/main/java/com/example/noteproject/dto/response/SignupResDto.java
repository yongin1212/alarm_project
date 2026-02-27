package com.example.noteproject.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupResDto {

    @NotNull
    private boolean success;

    @NotNull
    private String message;
}

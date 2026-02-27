package com.example.noteproject.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResDto {

    @NotNull
    private boolean success;

    @NotNull
    private String message;

    private String accesstoken;

    private String refreshtoken;
}

package com.example.noteproject.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.sql.Update;

@Getter
@Setter
@NoArgsConstructor
public class AuthResDto {

    @NotNull
    private boolean success;

    @NotNull
    private String message;
}

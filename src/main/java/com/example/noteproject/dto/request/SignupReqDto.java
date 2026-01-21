package com.example.noteproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SignupReqDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String checkedPassword;
}

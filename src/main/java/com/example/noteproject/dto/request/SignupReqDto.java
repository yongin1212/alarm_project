package com.example.noteproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SignupReqDto {

    @NotBlank
    @Size(min = 4, max = 16)
    private String username;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;

    @NotBlank
    @Size(min = 8, max = 16)
    private String confirmPassword;
}

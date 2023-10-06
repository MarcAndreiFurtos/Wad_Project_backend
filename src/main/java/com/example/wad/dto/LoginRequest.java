package com.example.wad.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message="username is required")
    private String username;
    @NotBlank(message="password is required")
    private String password;

}

package com.example.wad.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenRequest {
    @NotEmpty
    private String refreshToken;
    private String username;

}

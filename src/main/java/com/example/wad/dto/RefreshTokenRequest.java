package com.example.wad.dto;

import com.example.wad.Entities.RefreshToken;
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

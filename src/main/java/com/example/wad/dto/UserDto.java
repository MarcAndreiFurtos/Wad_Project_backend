package com.example.wad.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    private Instant created;
    private boolean banned;
    private boolean isAdmin;

    public Long createUserId(){
        UUID uuid = UUID.randomUUID();

        long id = uuid.getLeastSignificantBits();

        return id;
    }
}

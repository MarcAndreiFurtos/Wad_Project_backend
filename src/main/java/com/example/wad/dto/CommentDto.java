package com.example.wad.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long postId;
    private Long id;
    private Instant createdDate;
    @NotBlank
    private String text;
    private String username;

}

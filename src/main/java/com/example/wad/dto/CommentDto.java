package com.example.wad.dto;

import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long postId;
    private Long id;
    private Instant createdDate;
    @NonNull
    private String text;
    private String username;

}

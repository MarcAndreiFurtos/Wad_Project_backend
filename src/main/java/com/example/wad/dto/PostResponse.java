package com.example.wad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    private String postName;
    private String url;
    private String description;
    private Integer voteCount;
    private Integer commentCount;
    private String userName;
    private String duration;
    private boolean upVote;
    private boolean downVote;
}

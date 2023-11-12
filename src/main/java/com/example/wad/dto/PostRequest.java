package com.example.wad.dto;



import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
//    private Long postId;
    @NotBlank(message = "post name can't be blank")
    private String postName;
    private String url;
    private String description;
}

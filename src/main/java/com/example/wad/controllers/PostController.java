package com.example.wad.controllers;

import com.example.wad.Entities.Post;
import com.example.wad.dto.PostRequest;
import com.example.wad.dto.PostResponse;
import com.example.wad.service.PostService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Void> createPost(@RequestBody  PostRequest postRequest){
        log.info(postRequest.getPostName());
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id){
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("/user")
    public ResponseEntity<List<PostResponse>> getPostByUsername(@RequestParam String username){
        return status(HttpStatus.OK).body(postService.getAllPostsByUser(username));
    }
}

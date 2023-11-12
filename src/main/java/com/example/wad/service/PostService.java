package com.example.wad.service;

import com.example.wad.Entities.Post;
import com.example.wad.Entities.User;
import com.example.wad.dto.PostRequest;
import com.example.wad.dto.PostResponse;
import com.example.wad.exceptions.SpringRedditException;
import com.example.wad.mapper.PostMapper;
import com.example.wad.repository.PostRepository;
import com.example.wad.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authService;
    private final PostMapper postMapper;
    public void save(PostRequest postRequest){
        log.info(postRequest.getDescription());
        Post post = new Post();
        post.setCreatedDate(Instant.now());
        post.setPostName(postRequest.getPostName());
        post.setUrl(postRequest.getUrl());
        post.setDescription(postRequest.getDescription());
        post.setVoteCount(0);
        post.setUser(userRepository.findByUserName("kkkk").get());
        log.info(post.getDescription());
        postRepository.save(post);
    }

    @Transactional
    public PostResponse getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new SpringRedditException("post not found"));
        return postMapper.mapToDto(post);
    }

    public List<PostResponse> getAllPosts(){
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .peek(postResponse -> log.info("PostResponse: {}", postResponse.getPostName()))
                .collect(toList());
    }

    public List<PostResponse> getAllPostsByUser(String userName){
        User user = userRepository.findByUserName(userName).orElseThrow(()->new SpringRedditException("user not found"));
        return postRepository.findAllByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    public void deletePostById(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(()-> new SpringRedditException("post not found"));
        postRepository.delete(post);
    }
}

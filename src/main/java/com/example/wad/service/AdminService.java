package com.example.wad.service;

import com.example.wad.Entities.Post;
import com.example.wad.Entities.User;
import com.example.wad.exceptions.SpringRedditException;
import com.example.wad.repository.PostRepository;
import com.example.wad.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.wad.service.AuthenticationService;
import com.example.wad.service.PostService;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final PostRepository postRepository;
    private final PostService postService;

    @Transactional
    public void BanUser(String userName){
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        user.setBanned(true);
        userRepository.save(user);
    }

    @Transactional
    public void DeletePost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new SpringRedditException("post not found"));
        postService.deletePostById(postId);
    }
}

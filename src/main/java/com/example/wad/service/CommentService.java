package com.example.wad.service;

import com.example.wad.Entities.Comment;
import com.example.wad.Entities.User;
import com.example.wad.dto.CommentDto;
import com.example.wad.exceptions.SpringRedditException;
import com.example.wad.mapper.CommentMapper;
import com.example.wad.repository.CommentRepository;
import com.example.wad.repository.PostRepository;
import com.example.wad.repository.UserRepository;
import com.example.wad.Entities.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.wad.exceptions.UserBannedException;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;


    public void saveComment(CommentDto commentDto){
        if (authService.getCurrentUser().isBanned()){
            throw new UserBannedException("the user" + authService.getCurrentUser().getUserName() + "is banned and can't currently comment");
        }
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(()->new SpringRedditException("post " + commentDto.getPostId().toString() + "not found"));
        Comment comment = commentMapper.map(commentDto,post,authService.getCurrentUser());
        commentRepository.save(comment);
    }

    public List<CommentDto> getAllCommentsByPost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(()-> new SpringRedditException("post not found"));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .toList();
    }

    public List<CommentDto> getAllCommentsByUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow(()-> new SpringRedditException("user not found"));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .toList();
    }
}

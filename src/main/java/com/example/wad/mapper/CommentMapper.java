package com.example.wad.mapper;

import com.example.wad.Entities.Comment;
import com.example.wad.Entities.Post;
import com.example.wad.Entities.User;
import com.example.wad.dto.CommentDto;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CommentMapper {
    public Comment map(CommentDto commentDto, Post post, User user) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setCreatedDate(Instant.now());
        comment.setPost(post);
        comment.setUser(user);
        return comment;
    }

    public CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setPostId(comment.getPost().getPostId());
        commentDto.setUsername(comment.getUser().getUserName());
        // Set other properties of CommentDto if available in Comment entity
        return commentDto;
    }
}

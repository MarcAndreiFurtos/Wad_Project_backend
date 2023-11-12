package com.example.wad.mapper;

import com.example.wad.Entities.Post;
import com.example.wad.Entities.Vote;
import com.example.wad.Entities.VoteType;
import com.example.wad.dto.PostRequest;
import com.example.wad.dto.PostResponse;
import com.example.wad.repository.CommentRepository;
import com.example.wad.repository.UserRepository;
import com.example.wad.repository.VoteRepository;
import com.example.wad.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class PostMapper {
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final AuthenticationService authService;
    private final UserRepository userRepository;
    @Autowired
    public PostMapper(CommentRepository commentRepository, VoteRepository voteRepository, AuthenticationService authService, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.voteRepository = voteRepository;
        this.authService = authService;
        this.userRepository = userRepository;
    }

    public Post map(PostRequest postRequest) {
        log.info(postRequest.getPostName());
        Post post = new Post();
        post.setDescription(postRequest.getDescription());
        post.setPostName(postRequest.getPostName());
        post.setUrl(postRequest.getUrl());
        post.setPostId(UUID.randomUUID().getMostSignificantBits());
        post.setVoteCount(0);
        post.setUser(userRepository.findByUserName("kkkk").get());
        post.setCreatedDate(Instant.now());
        log.info(post.getPostName());
        return post;
    }

    public PostResponse mapToDto(Post post) {
        log.info(post.getDescription());
        PostResponse postResponse = new PostResponse();
        postResponse.setPostId(post.getPostId());
        postResponse.setPostName(post.getPostName());
        postResponse.setUrl(post.getUrl());
        postResponse.setDescription(post.getDescription());
        postResponse.setVoteCount(post.getVoteCount());
        postResponse.setUserName(post.getUser().getUserName());
        postResponse.setCommentCount(commentCount(post));
        postResponse.setUpVote(isPostUpVoted(post));
        postResponse.setDownVote(isPostDownVoted(post));
        return postResponse;
    }

    private int commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    private boolean isPostUpVoted(Post post) {
        return checkVoteType(post, VoteType.UPVOTE);
    }

    private boolean isPostDownVoted(Post post) {
        return checkVoteType(post, VoteType.DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
        }
        return false;
    }
}

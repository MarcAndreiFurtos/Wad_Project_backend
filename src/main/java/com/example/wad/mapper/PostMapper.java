package com.example.wad.mapper;

import com.example.wad.Entities.Post;
import com.example.wad.Entities.User;
import com.example.wad.Entities.Vote;
import com.example.wad.Entities.VoteType;
import com.example.wad.dto.PostRequest;
import com.example.wad.dto.PostResponse;
import com.example.wad.repository.CommentRepository;
import com.example.wad.repository.VoteRepository;
import com.example.wad.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class PostMapper {
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final AuthenticationService authService;

    @Autowired
    public PostMapper(CommentRepository commentRepository, VoteRepository voteRepository, AuthenticationService authService) {
        this.commentRepository = commentRepository;
        this.voteRepository = voteRepository;
        this.authService = authService;
    }

    public Post map(PostRequest postRequest, User user) {
        Post post = new Post();
        post.setCreatedDate(Instant.now());
        post.setDescription(postRequest.getText());
        post.setVoteCount(0);
        post.setUser(user);
        return post;
    }

    public PostResponse mapToDto(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setPostId(post.getPostId());
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

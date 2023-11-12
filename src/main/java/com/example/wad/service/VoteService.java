package com.example.wad.service;


import com.example.wad.Entities.Post;
import com.example.wad.Entities.Vote;
import com.example.wad.Entities.VoteType;
import com.example.wad.dto.VoteDto;
import com.example.wad.exceptions.SpringRedditException;
import com.example.wad.repository.PostRepository;
import com.example.wad.repository.UserRepository;
import com.example.wad.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public void vote(VoteDto voteDto){
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(()->new SpringRedditException("post not found"));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,userRepository.findByUserName("kkkk").get());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new SpringRedditException("you allready voted");
        }
        if (VoteType.UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount()+1);
        }
        else if (VoteType.DOWNVOTE.equals(voteDto.getVoteType())) {
           post.setVoteCount(post.getVoteCount()-1);
        }
        voteRepository.save(mapToVote(voteDto,post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(userRepository.findByUserName("kkkk").get())
                .build();
    }
}

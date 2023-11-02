package com.example.wad.config;

import com.example.wad.Entities.Post;
import com.example.wad.Entities.User;
import com.example.wad.dto.PostRequest;
import com.example.wad.dto.PostResponse;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.wad.mapper.PostMapper;
import com.example.wad.repository.CommentRepository;
import com.example.wad.repository.VoteRepository;
import com.example.wad.service.AuthenticationService;

//@Configuration
//public class MapperConfiguration {
//    @Bean
//    public PostMapper postMapper(CommentRepository commentRepository, VoteRepository voteRepository, AuthenticationService authService) {
//        return Mappers.getMapper(PostMapper.class);
//    }
//}

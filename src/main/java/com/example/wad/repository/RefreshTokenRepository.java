package com.example.wad.repository;


import com.example.wad.Entities.Comment;
import com.example.wad.Entities.Post;
import com.example.wad.Entities.RefreshToken;
import com.example.wad.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
}

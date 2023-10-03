package com.example.wad.repository;

import com.example.wad.Entities.Comment;
import com.example.wad.Entities.Post;
import com.example.wad.Entities.User;
import com.example.wad.Entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByToken(String token);
}

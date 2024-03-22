package com.epam.hibernate.repository;

import com.epam.hibernate.entity.RefreshToken;
import com.epam.hibernate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findRefreshTokenByToken(String token);
    @Modifying
    int deleteByUser(User user);
}

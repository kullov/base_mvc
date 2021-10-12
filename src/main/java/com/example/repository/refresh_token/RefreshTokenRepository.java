package com.example.repository.refresh_token;

import com.example.entity.refresh_token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The interface Refresh token repository.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    /**
     * Find by token equals optional.
     *
     * @param token the token
     * @return the optional
     */
    Optional<RefreshToken> findByTokenEquals(String token);

    /**
     * Find by username equals and is active is true.
     *
     * @param username the username
     * @return the optional
     */
    @Query("SELECT rf FROM RefreshToken rf " +
           "WHERE rf.isActive = TRUE " +
           "AND rf.userId = (SELECT u.id FROM User u WHERE u.username = :username AND u.isDeleted = FALSE)")
    Optional<RefreshToken> findByTokenIsActiveTrueAndUsernameEquals(@Param("username") String username);

    /**
     * Deactivate the refresh token by id.
     *
     * @param id the refresh token id
     */
    @Modifying
    @Query("UPDATE RefreshToken rf SET rf.isActive = false WHERE rf.id = :id")
    void deactivateByTokenId(@Param("id") int id);

    /**
     * Deactivate all refresh tokens by user id.
     *
     * @param userId the user id
     */
    @Modifying
    @Query("UPDATE RefreshToken rf SET rf.isActive = false WHERE rf.userId = :userId")
    void deactivateAllByUserId(@Param("userId") int userId);

    /**
     * Delete all by user id equals.
     *
     * @param userId the user id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Modifying
    @Query("DELETE FROM RefreshToken rf WHERE rf.userId = :userId")
    void deleteAllByUserIdEquals(@Param("userId") int userId);
}
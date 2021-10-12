package com.example.repository.user;

import com.example.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * The interface User repository.
 *
 * @author MinhNB<br>
 * <p>Mail: minhnb.it@pm.me</p>
 * @since 2021-10-09
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find by username and is deleted false.
     *
     * @param username the username
     * @return the {@link Optional#empty()} if no result, otherwise return the optional of user
     */
    Optional<User> findByUsernameAndIsDeletedFalse(String username);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    @Modifying
    @Query(value = "UPDATE User u SET u.isDeleted = false WHERE u.id = :id")
    void deleteById(@Param(value = "id") int id);

    /**
     * Update last login time.
     *
     * @param id        the id
     * @param loginTime the login time
     */
    @Modifying
    @Query(value = "UPDATE User u SET u.lastLoginTime = :lastLoginTime WHERE u.id = :id")
    void updateLastLoginTime(@Param(value = "id") int id, @Param(value = "lastLoginTime") OffsetDateTime loginTime);

    @Query(value = "SELECT username FROM User WHERE id = :id")
    String findUsernameById(@Param(value = "id") int id);
}

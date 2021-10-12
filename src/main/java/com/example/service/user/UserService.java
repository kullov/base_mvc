package com.example.service.user;

import com.example.common.exception.BusinessException;
import com.example.dto.user.UserRequest;
import com.example.dto.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * The interface User account service.
 *
 * @author MinhNB<br>
 * <p>Mail: minhnb.it@pm.me</p>
 * @since 2021-09-18
 */
public interface UserService {

    /**
     * Gets all.
     *
     * @return the all users
     */
    List<UserResponse> getAll();

    /**
     * Gets all paging.
     *
     * @param pageRequest the page request
     * @return the user paging
     */
    Page<UserResponse> getAllPaging(Pageable pageRequest);


    /**
     * Gets by id.
     *
     * @param id the id
     * @return the user response
     * @throws BusinessException the business exception
     */
    UserResponse getById(int id) throws BusinessException;

    /**
     * Gets available user by username.
     *
     * @param username the username
     * @return the user response
     * @throws BusinessException the business exception
     */
    UserResponse getByUsername(String username) throws BusinessException;

    /**
     * Create new user.
     *
     * @param newUser the new user
     * @return the user response
     * @throws BusinessException the business exception
     */
    UserResponse create(UserRequest newUser) throws BusinessException;

    /**
     * Update info user.
     *
     * @param updateUser the updated user
     * @return the user response
     * @throws BusinessException the business exception
     */
    UserResponse update(UserRequest updateUser) throws BusinessException;

    /**
     * Delete user by id.
     *
     * @param id the id
     * @throws BusinessException the business exception
     */
    void delete(int id) throws BusinessException;

    /**
     * Update last login time.
     *
     * @param id        the user id
     * @param loginTime the login time
     * @throws BusinessException the business exception
     */
    void updateLastLoginTime(int id, OffsetDateTime loginTime) throws BusinessException;
}

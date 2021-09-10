package com.example.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.common.exception.BusinessException;
import com.example.dto.user.UserRequest;
import com.example.dto.user.UserResponse;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<UserResponse> findAll();

    /**
     * Find all paging page.
     *
     * @param pageRequest the page request
     * @return the page
     */
    public Page<UserResponse> findAllPaging(Pageable pageRequest);

    /**
     * Find by id user response.
     *
     * @param id the id
     * @return the user response
     * @throws BusinessException the business exception
     */
    public UserResponse findById(long id) throws BusinessException;

    /**
     * Save user user response.
     *
     *
     * @param userId
     * @param dto the dto
     * @return the user response
     * @throws BusinessException the business exception
     */
    public UserResponse saveUser(Long userId, UserRequest dto) throws BusinessException;

    /**
     * Delete user.
     *
     * @param id the id
     * @throws BusinessException the business exception
     */
    public void deleteUser(long id) throws BusinessException;
}

package com.example.service.user;

import com.example.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.common.exception.BusinessException;
import com.example.common.exception.message.BusinessMessage;
import com.example.common.http.Response;
import com.example.dto.user.UserRequest;
import com.example.dto.user.UserResponse;
import com.example.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type User service.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * The User repository.
     */
    protected final UserRepository userRepository;

    /**
     * Find all list.
     *
     * @return the list
     */
    @Override
    public List<UserResponse> findAll() {
        return this.userRepository.findAll().stream().map(UserResponse::convertFromEntity).collect(Collectors.toList());
    }

    /**
     * Find all paging page.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<UserResponse> findAllPaging(Pageable pageable) {
        return this.userRepository.findAll(pageable).map(UserResponse::convertFromEntity);
    }

    /**
     * Gets entity by id.
     *
     * @param id the id
     * @return the entity by id
     * @throws BusinessException the business exception
     */
    public User gettEntityById(long id) throws BusinessException {
        Optional<User> entity = this.userRepository.findById(id);
        if (entity.isPresent()) {
            throw new BusinessException(BusinessMessage.NOT_FOUND, Response.CODE_BUSINESS);
        }
        return entity.get();
    }

    /**
     * Find by id user response.
     *
     * @param id the id
     * @return the user response
     * @throws BusinessException the business exception
     */
    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(long id) throws BusinessException {
        return UserResponse.convertFromEntity(this.gettEntityById(id));
    }

    /**
     * Save user user response.
     *
     *
     * @param userId
     * @param dto the dto
     * @return the user response
     * @throws BusinessException the business exception
     */
    @Override
    public UserResponse saveUser(Long userId, UserRequest dto) throws BusinessException {
        return null;
    }

    /**
     * Delete user.
     *
     * @param id the id
     * @throws BusinessException the business exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(long id) throws BusinessException {
        User user = this.gettEntityById(id);
        this.userRepository.deleteById(user.getId());
    }
}

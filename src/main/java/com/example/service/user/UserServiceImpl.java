package com.example.service.user;

import com.example.common.enums.Authority;
import com.example.common.exception.BusinessException;
import com.example.common.exception.message.BusinessMessage;
import com.example.common.http.Response;
import com.example.dto.user.UserRequest;
import com.example.dto.user.UserResponse;
import com.example.entity.user.User;
import com.example.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class User service implement.
 *
 * @author MinhNB<br>
 * <p>Mail: minhnb.it@pm.me</p>
 * @since 2021-10-09
 */
@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    /**
     * The User repository.
     */
    protected final UserRepository userRepository;

    /**
     * The Password encoder.
     */
    protected final PasswordEncoder passwordEncoder;

    /**
     * Gets all.
     *
     * @return the all users
     */
    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> getAll() {
        return this.userRepository.findAll().stream().map(UserResponse::fromEntity).collect(Collectors.toList());
    }

    /**
     * Gets all paging.
     *
     * @param pageRequest the page request
     * @return the user paging
     */
    @Transactional(readOnly = true)
    @Override
    public Page<UserResponse> getAllPaging(Pageable pageRequest) {
        return null;
    }

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the user response
     * @throws BusinessException the business exception
     */
    @Transactional(readOnly = true)
    @Override
    public UserResponse getById(int id) throws BusinessException {
        return this.userRepository.findById(id)
                                  .map(UserResponse::fromEntity)
                                  .orElseThrow(() -> new BusinessException(BusinessMessage.NOT_FOUND, Response.CODE_BUSINESS));
    }

    /**
     * Gets available user by username.
     *
     * @param username the username
     * @return the user response
     * @throws BusinessException the business exception
     */
    @Transactional(readOnly = true)
    @Override
    public UserResponse getByUsername(String username) throws BusinessException {
        return this.userRepository.findByUsernameAndIsDeletedFalse(username)
                                  .map(UserResponse::fromEntity)
                                  .orElseThrow(() -> new BusinessException(BusinessMessage.NOT_FOUND, Response.CODE_BUSINESS));
    }

    /**
     * Create new user.
     *
     * @param newUser the new user
     * @return the user response
     * @throws BusinessException the business exception
     */
    @Override
    public UserResponse create(UserRequest newUser) throws BusinessException {
        User newUserEntity = UserRequest.toEntity(newUser);

        newUserEntity.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
        newUserEntity.setAuthority(Authority.USER);

        return UserResponse.fromEntity(this.userRepository.saveAndFlush(newUserEntity));
    }

    /**
     * Update info user.
     *
     * @param updateUser the updated user
     * @return the user response
     * @throws BusinessException the business exception
     */
    @Override
    public UserResponse update(UserRequest updateUser) throws BusinessException {
        User currentUser = this.userRepository.findById(updateUser.getId())
                                              .orElseThrow(() -> new BusinessException(BusinessMessage.NOT_FOUND));

        if (StringUtils.hasText(updateUser.getPassword())) {
            updateUser.setPassword(this.passwordEncoder.encode(updateUser.getPassword()));
        } else {
            updateUser.setPassword(currentUser.getPassword());
        }

        return UserResponse.fromEntity(this.userRepository.save(UserRequest.toEntity(updateUser)));
    }

    /**
     * Delete user.
     *
     * @param id the id
     * @throws BusinessException the business exception
     */
    @Override
    public void delete(int id) throws BusinessException {
        this.userRepository.deleteById(id);
    }

    /**
     * Update last login time.
     *
     * @param id        the user id
     * @param loginTime the login time
     * @throws BusinessException the business exception
     */
    @Override
    public void updateLastLoginTime(int id, OffsetDateTime loginTime) throws BusinessException {
        this.userRepository.updateLastLoginTime(id, loginTime);
    }
}
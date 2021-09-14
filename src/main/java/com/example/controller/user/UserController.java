package com.example.controller.user;

import com.example.common.exception.BusinessException;
import com.example.common.http.PagingRequest;
import com.example.common.utils.AppUtils;
import com.example.dto.user.UserRequest;
import com.example.dto.user.UserResponse;
import com.example.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.example.common.http.Response;

import javax.validation.Valid;
import java.util.List;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/webapi/v1/user")
@RequiredArgsConstructor
public class UserController {

    /**
     * The User service.
     */
    protected final UserService userService;

    /**
     * Gets all user paging.
     *
     * @param pagingRequest the paging request
     * @return the all user paging
     */
    @GetMapping()
    public Response<Page<UserResponse>> getAllUserPaging(PagingRequest pagingRequest) {
        Pageable paging = AppUtils.getPaging(pagingRequest);
        return Response.<Page<UserResponse>>ok().data(this.userService.findAllPaging(paging));
    }

    @GetMapping("/all")
    public Response<List<UserResponse>> getAllUsers() {
        return Response.<List<UserResponse>>ok().data(this.userService.findAll());
    }

    /**
     * Gets one user.
     *
     * @param id the id
     * @return the one user
     * @throws BusinessException the business exception
     */
    @GetMapping("/{id}")
    public Response<UserResponse> getOneUser(@PathVariable Long id) throws BusinessException {
        return Response.<UserResponse>ok().data(this.userService.findById(id));
    }

    /**
     * Create user response.
     *
     * @param userRequest the user request
     * @return the response
     * @throws BusinessException the business exception
     */
    @PostMapping()
    public Response<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) throws BusinessException {
        return Response.<UserResponse>ok().data(this.userService.saveUser(null, userRequest));
    }

    /**
     * Delete user response.
     *
     * @param userId      the user id
     * @param userRequest the user request
     * @return the response
     * @throws BusinessException the business exception
     */
    @PutMapping("/{userId}")
    public Response<UserResponse> deleteUser(@PathVariable Long userId, @Valid @RequestBody UserRequest userRequest) throws BusinessException {
        return Response.<UserResponse>ok().data(this.userService.saveUser(userId, userRequest));
    }

    /**
     * Delete user response.
     *
     * @param userId the user id
     * @return the response
     * @throws BusinessException the business exception
     */
    @DeleteMapping("/{userId}")
    public Response<Boolean> deleteUser(@PathVariable Long userId) throws BusinessException {
        this.userService.deleteUser(userId);
        return Response.<Boolean>ok().data(true);
    }
}

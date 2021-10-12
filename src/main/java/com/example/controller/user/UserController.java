package com.example.controller.user;

import com.example.common.exception.BusinessException;
import com.example.common.http.PagingRequest;
import com.example.common.http.Response;
import com.example.common.utils.AppUtils;
import com.example.dto.user.UserRequest;
import com.example.dto.user.UserResponse;
import com.example.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/paging")
    public Response<Page<UserResponse>> getAllUserPaging(PagingRequest pagingRequest) {
        Pageable paging = AppUtils.getPaging(pagingRequest);
        return Response.<Page<UserResponse>>ok().data(this.userService.getAllPaging(paging));
    }

    /**
     * Gets all users.
     *
     * @return the all users
     */
    @GetMapping()
    public Response<List<UserResponse>> getAllUsers() {
        return Response.<List<UserResponse>>ok().data(this.userService.getAll());
    }

    /**
     * Gets one user.
     *
     * @param id the id
     * @return the one user
     * @throws BusinessException the business exception
     */
    @GetMapping("/{id}")
    public Response<UserResponse> getOneUser(@PathVariable(name = "id") Integer id) throws BusinessException {
        return Response.<UserResponse>ok().data(this.userService.getById(id));
    }

    /**
     * Sign up new user.
     *
     * @param userRequest the user request
     * @return the response
     * @throws BusinessException the business exception
     */
    @PostMapping()
    public Response<UserResponse> create(@Valid @RequestBody UserRequest userRequest) throws BusinessException {
        return Response.<UserResponse>ok().data(this.userService.create(userRequest));
    }

    /**
     * Update user by id.
     *
     * @param userId      the user id
     * @param userRequest the user request
     * @return the response
     * @throws BusinessException the business exception
     */
    @PutMapping("/{id}")
    public Response<UserResponse> update(@PathVariable(name = "id") Integer userId,
                                         @Valid @RequestBody UserRequest userRequest) throws BusinessException {
        userRequest.setId(userId);
        return Response.<UserResponse>ok().data(this.userService.update(userRequest));
    }

    /**
     * Delete user by id.
     *
     * @param userId the user id
     * @return the response
     * @throws BusinessException the business exception
     */
    @DeleteMapping("/{id}")
    public Response<Boolean> delete(@PathVariable(name = "id") Integer userId) throws BusinessException {
        this.userService.delete(userId);
        return Response.<Boolean>ok().data(true);
    }
}

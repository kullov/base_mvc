package org.ttnga.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.ttnga.common.exception.BusinessException;
import org.ttnga.common.http.PagingRequest;
import org.ttnga.common.http.Response;
import org.ttnga.common.utils.AppUtils;
import org.ttnga.dto.user.UserRequest;
import org.ttnga.dto.user.UserResponse;
import org.ttnga.service.user.UserService;

import javax.validation.Valid;

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

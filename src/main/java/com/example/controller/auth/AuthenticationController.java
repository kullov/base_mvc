package com.example.controller.auth;

import com.example.common.exception.BusinessException;
import com.example.common.http.Response;
import com.example.dto.authentication.SignInRequest;
import com.example.dto.authentication.SignInResponse;
import com.example.dto.refresh_token.RefreshTokenResponse;
import com.example.dto.user.UserResponse;
import com.example.security.jwt.JwtProvider;
import com.example.security.userdetails.UserDetailsImpl;
import com.example.service.refresh_token.RefreshTokenService;
import com.example.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * The class Authentication controller.
 *
 * @author MinhNB<br>
 * <p>Mail: minhnb.it@pm.me</p>
 * @since 2021-10-09
 */
@RestController
@RequestMapping("/webapi/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    /**
     * The Authentication manager.
     */
    protected final AuthenticationManager authenticationManager;

    /**
     * The Jwt provider.
     */
    protected final JwtProvider jwtProvider;

    /**
     * The User service.
     */
    protected final UserService userService;

    /**
     * The Refresh token service.
     */
    protected final RefreshTokenService refreshTokenService;

    /**
     * Sign in.
     *
     * @param signInRequest the sign in request
     * @return the response
     */
    @PostMapping("/sign-in")
    public Response<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
        final OffsetDateTime signInTime = OffsetDateTime.now(ZoneOffset.UTC);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserResponse loginUser = this.userService.getByUsername(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
        this.userService.updateLastLoginTime(loginUser.getId(), signInTime);
        loginUser.setLastLoginTime(signInTime);

        final String refreshToken = this.refreshTokenService.create(loginUser.getId()).getToken();
        final String jwtToken = this.jwtProvider.generateToken(loginUser.getUsername());

        return Response.<SignInResponse>ok().data(new SignInResponse(refreshToken, jwtToken, loginUser));
    }

    /**
     * Handle refresh token.
     *
     * @param refreshToken the refresh token
     * @return the response
     * @throws BusinessException the business exception
     */
    @PostMapping("/refresh-token")
    public Response<RefreshTokenResponse> refreshToken(@Valid @RequestBody String refreshToken) throws BusinessException {
        return Response.<RefreshTokenResponse>ok().data(this.refreshTokenService.refreshToken(refreshToken));
    }
}
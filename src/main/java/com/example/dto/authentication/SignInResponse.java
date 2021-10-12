package com.example.dto.authentication;

import com.example.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class Sign in request.
 *
 * @author MinhNB<br>
 * <p>Mail: minhnb.it@pm.me</p>
 * @since 2021-10-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {

    /**
     * The Refresh token.
     */
    private String refreshToken;

    /**
     * The JWT token.
     */
    private String jwtToken;

    /**
     * The User.
     */
    private UserResponse user;
}
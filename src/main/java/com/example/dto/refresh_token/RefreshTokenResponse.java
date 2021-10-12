package com.example.dto.refresh_token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class Refresh token response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenResponse {

    /**
     * The Refresh token.
     */
    private String refreshToken;

    /**
     * The JWT token.
     */
    private String jwtToken;
}
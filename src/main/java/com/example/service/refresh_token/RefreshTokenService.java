package com.example.service.refresh_token;

import com.example.common.exception.BusinessException;
import com.example.dto.refresh_token.RefreshTokenResponse;
import com.example.entity.refresh_token.RefreshToken;
import lombok.NonNull;

import java.util.List;

/**
 * The interface Refresh token service.
 *
 * @author MinhNB<br>
 * <p>Mail: minhnb.it@pm.me</p>
 * @since 2021-10-09
 */
public interface RefreshTokenService {

    /**
     * Create refresh token.
     *
     * @param userId the user id
     * @return the refresh token
     * @throws BusinessException the business exception
     */
    RefreshToken create(int userId) throws BusinessException;

    /**
     * Delete by ids.
     *
     * @param ids the ids
     * @throws BusinessException the business exception
     */
    void deleteByIds(@NonNull List<Integer> ids) throws BusinessException;

    /**
     * Handle refresh token.
     *
     * @param refreshTokenUsing the refresh token using
     * @return the refresh token response
     */
    RefreshTokenResponse refreshToken(@NonNull String refreshTokenUsing);
}
package com.example.service.refresh_token;

import com.example.common.exception.BusinessException;
import com.example.common.exception.message.BusinessMessage;
import com.example.common.http.Response;
import com.example.dto.refresh_token.RefreshTokenResponse;
import com.example.entity.refresh_token.RefreshToken;
import com.example.repository.refresh_token.RefreshTokenRepository;
import com.example.repository.user.UserRepository;
import com.example.security.jwt.JwtProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

/**
 * The class Refresh token service.
 *
 * @author MinhNB<br>
 * <p>Mail: minhnb.it@pm.me</p>
 * @since 2021-10-09
 */
@RequiredArgsConstructor
@Transactional
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    /**
     * The Seconds block use refresh token.
     */
    private final int SECONDS_BLOCK_USE_REFRESH_TOKEN = 60;

    /**
     * The Expiration days.
     */
    @Value("${security.refresh-token.expiration}")
    private long EXPIRATION_DAYS;

    /**
     * The Jwt provider.
     */
    protected final JwtProvider jwtProvider;

    /**
     * The Refresh token repo.
     */
    protected final RefreshTokenRepository refreshTokenRepo;

    /**
     * The User repository.
     */
    protected final UserRepository userRepository;

    /**
     * Create refresh token.
     *
     * @param userId the user id
     * @return the refresh token
     * @throws BusinessException the business exception
     */
    @Override
    public RefreshToken create(int userId) throws BusinessException {
        this.refreshTokenRepo.deactivateAllByUserId(userId);

        final LocalDate expiryDate = LocalDate.now(ZoneOffset.UTC).plusDays(this.EXPIRATION_DAYS);
        RefreshToken newRefreshToken = RefreshToken.builder()
                                                   .token(UUID.randomUUID().toString())
                                                   .userId(userId)
                                                   .isActive(true)
                                                   .expiryDate(expiryDate)
                                                   .build();

        return this.refreshTokenRepo.save(newRefreshToken);
    }

    /**
     * Delete by ids.
     *
     * @param ids the ids
     * @throws BusinessException the business exception
     */
    @Override
    public void deleteByIds(@NonNull List<Integer> ids) throws BusinessException {
        boolean hasInvalidId = ids.stream().anyMatch(id -> (id == null || id < 0));

        if (ids.isEmpty() || hasInvalidId) {
            throw new BusinessException(BusinessMessage.INVALID_VALUE, Response.CODE_VALIDATION);
        }

        this.refreshTokenRepo.deleteAllById(ids);
    }

    /**
     * Handle refresh token.
     *
     * @param refreshTokenUsing the refresh token using
     * @return the refresh token response
     */
    @Override
    public RefreshTokenResponse refreshToken(@NonNull String refreshTokenUsing) {
        RefreshToken currentRefreshToken = this.refreshTokenRepo
                                                    .findByTokenEquals(refreshTokenUsing)
                                                    .orElseThrow(() -> new BusinessException(BusinessMessage.NOT_FOUND));
        int userId = currentRefreshToken.getUserId();

        if (currentRefreshToken.getExpiryDate().isBefore(LocalDate.now(ZoneOffset.UTC))) {
            // Refresh token is expired. User must re-login
            this.refreshTokenRepo.deactivateByTokenId(currentRefreshToken.getId());
            throw new BusinessException(BusinessMessage.EXPIRED_REFRESH_TOKEN);
        }

        if (!currentRefreshToken.getIsActive()) {
            // Logout user from all devices
            this.refreshTokenRepo.deleteAllByUserIdEquals(userId);
            throw new BusinessException(BusinessMessage.USING_INACTIVE_REFRESH_TOKEN);
        }

        if (isSpamApiRefreshToken(currentRefreshToken)) {
            throw new BusinessException(BusinessMessage.SPAM_API_REFRESH_TOKEN);
        }

        this.refreshTokenRepo.deactivateByTokenId(currentRefreshToken.getId());
        RefreshToken newRefreshToken = this.create(userId);
        String newJwtToken = this.jwtProvider.generateToken(this.userRepository.findUsernameById(userId));

        return new RefreshTokenResponse(newRefreshToken.getToken(), newJwtToken);
    }

    /**
     * Is spam api refresh token.
     *
     * @param refreshToken the refresh token
     * @return the boolean
     */
    private boolean isSpamApiRefreshToken(@NonNull RefreshToken refreshToken) {
        final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        return refreshToken.getCreatedTime().plusSeconds(this.SECONDS_BLOCK_USE_REFRESH_TOKEN).isAfter(now);
    }
}
package com.example.security.jwt;

import com.example.common.exception.BusinessException;
import com.example.common.exception.message.BusinessMessage;
import com.example.common.http.Response;
import com.example.entity.user.User;
import com.example.repository.refresh_token.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

/**
 * The class Jwt provider.
 *
 * @author MinhNB<br>
 * <p>Mail: minhnb.it@pm.me</p>
 * @since 2021-09-17
 */
@Slf4j
@Component
public class JwtProvider {

    /**
     * The Jwt secret key.
     */
    @Value("${security.jwt.secret-key}")
    private String JWT_SECRET_KEY;

    /**
     * The expiration of token by milliseconds.
     */
    @Value("${security.jwt.expiration}")
    private long JWT_EXPIRATION;

    /**
     * The Refresh token repository.
     */
    @Autowired
    private RefreshTokenRepository refreshTokenRepo;

    /**
     * Generate a token.
     *
     * @param username the user name
     * @return the JSON Web Token
     */
    public String generateToken(@NonNull String username) {
        SecretKey key = Keys.hmacShaKeyFor(this.JWT_SECRET_KEY.getBytes());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + this.JWT_EXPIRATION);

        return Jwts.builder()
                   .claim(User.Fields.username, username)
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(key, SignatureAlgorithm.HS512)
                   .compact();
    }

    /**
     * Gets username from token.
     *
     * @param token the JSON Web Token
     * @return the {@link Optional} of value username in token, otherwise {@link Optional#empty()}
     * @throws BusinessException if the token is invalid
     */
    public Optional<String> getUsernameFromToken(String token) throws BusinessException {
        if (this.validateToken(token)) {
            SecretKey key = Keys.hmacShaKeyFor(this.JWT_SECRET_KEY.getBytes());
            Jws<Claims> claimsJwt = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            final String username = claimsJwt.getBody().get(User.Fields.username, String.class);

            if (StringUtils.hasText(username)) {
                this.refreshTokenRepo.findByTokenIsActiveTrueAndUsernameEquals(username)
                                     .orElseThrow(() -> new BusinessException(BusinessMessage.USER_NO_ACTIVE_REFRESH_TOKEN,
                                                                              Response.CODE_UNAUTHORIZED)
                                     );
            }

            return Optional.ofNullable(username);
        }

        return Optional.empty();
    }

    /**
     * Validate token.
     *
     * @param token the JSON Web Token
     * @return the {@code true} if the token is valid
     * @throws JwtAuthenticationException if the token is invalid
     */
    public boolean validateToken(String token) throws JwtAuthenticationException {
        try {
            SecretKey key = Keys.hmacShaKeyFor(this.JWT_SECRET_KEY.getBytes());
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            throw new JwtAuthenticationException(BusinessMessage.JWT_EXPIRED_TOKEN);
        } catch (IllegalArgumentException | JwtException e) {
            e.printStackTrace();
            throw new JwtAuthenticationException(BusinessMessage.JWT_INVALID_TOKEN);
        }
    }
}
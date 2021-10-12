package com.example.security.jwt;

import com.example.security.userdetails.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * The class Jwt authentication filter.
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * The Authorization header.
     */
    private final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * The Bearer.
     */
    private final String BEARER = "Bearer";

    /**
     * The Jwt provider.
     */
    @Autowired
    private JwtProvider jwtProvider;

    /**
     * The User details service.
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * The Handler exception resolver.
     */
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    /**
     * The Jwt authentication entry point.
     */
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    /**
     * Do filter internal.
     *
     * @param request     the request
     * @param response    the response
     * @param filterChain the filter chain
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            Optional<String> username = this.getTokenFromRequest(request)
                                            .flatMap((this.jwtProvider::getUsernameFromToken));

            if (username.isPresent()) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username.get());
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // Continue doing request
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException e) {
            this.jwtAuthenticationEntryPoint.commence(request, response, e);
        } catch (Exception e) {
            this.exceptionResolver.resolveException(request, response, null, e);
        }
    }

    /**
     * Gets token from request.
     *
     * @param request the http request
     * @return the token from request
     */
    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(this.AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(this.BEARER)) {
            return Optional.of(bearerToken.substring(this.BEARER.length() + 1));
        }

        return Optional.empty();
    }
}
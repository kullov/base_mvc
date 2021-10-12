package com.example.config;

import com.example.security.jwt.JwtAuthenticationEntryPoint;
import com.example.security.jwt.JwtAuthenticationFilter;
import com.example.security.userdetails.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The class Web security config.
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * The constant Base url version 1.
     */
    private final String BASE_URL_V1 = "/webapi/v1";

    /**
     * The User details service.
     */
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * The Jwt authentication entry point.
     */
    private final JwtAuthenticationEntryPoint jwtAuthenEntryPoint;

    /**
     * Jwt authentication filter.
     *
     * @return the jwt authentication filter
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * Authentication manager bean.
     *
     * @return the authentication manager
     * @throws Exception the exception
     */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure.
     *
     * @param auth the authentication manager builder
     * @throws Exception the exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService)
                .passwordEncoder(this.passwordEncoder());
    }

    /**
     * Configuration CORS filter.
     *
     * @return the cors filter
     */
    @Bean
    public CorsFilter corsFilter() {
        List<String> allowedOriginPatterns = Collections.singletonList("http://*:[*]");
        List<String> allowedMethods = Stream.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE,
                                                HttpMethod.OPTIONS, HttpMethod.PATCH)
                                            .map(HttpMethod::name)
                                            .collect(Collectors.toList());
        final CorsConfiguration configuration = new CorsConfiguration().setAllowedOriginPatterns(allowedOriginPatterns);

        configuration.setAllowedMethods(Collections.unmodifiableList(allowedMethods));
        // setAllowedHeaders is important! Without it, OPTIONS preflight request will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Collections.singletonList("*"));
        // In case authentication is enabled, this flag MUST be set, otherwise CORS request will fail
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource corsConfig = new UrlBasedCorsConfigurationSource();
        corsConfig.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(corsConfig);
    }

    /**
     * Configure.
     *
     * @param http the http security
     * @throws Exception the exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Project using JWT to authenticate request so CSRF is disabled
                .cors().and()
                .exceptionHandling().authenticationEntryPoint(this.jwtAuthenEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(this.getAllPublicUrls()).permitAll()
                .antMatchers(HttpMethod.POST, this.BASE_URL_V1 + "/user").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(this.jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Get all public urls.
     *
     * @return the string array
     */
    private String[] getAllPublicUrls() {
        return new String[]{
                this.BASE_URL_V1 + "/auth/sign-in",
                this.BASE_URL_V1 + "/auth/refresh-token"
        };
    }
}

package com.example.security.userdetails;

import com.example.common.enums.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * The class User details.
 *
 * @author MinhNB<br>
 * <p>Mail: minhnb.it@pm.me</p>
 * @since 2021-09-17
 */
@RequiredArgsConstructor
public final class UserDetailsImpl implements UserDetails {

    /**
     * The Id.
     */
    private final int id;

    /**
     * The Username.
     */
    private final String username;

    /**
     * The Password.
     */
    private final String password;

    /**
     * The Authority.
     */
    private final Authority authority;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets authorities.
     *
     * @return the authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.authority.name()));
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Is account non expired boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Is account non locked boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Is credentials non expired boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Is enabled boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

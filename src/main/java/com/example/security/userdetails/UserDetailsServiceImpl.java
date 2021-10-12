package com.example.security.userdetails;

import com.example.common.exception.message.BusinessMessage;
import com.example.entity.user.User;
import com.example.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The class User details service.
 *
 * @author MinhNB<br>
 * <p>Mail: minhnb.it@pm.me</p>
 * @since 2021-09-17
 */
@Slf4j
@RequiredArgsConstructor
@Service
public final class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * The User repository.
     */
    private final UserRepository userRepository;

    /**
     * Load user by username.
     *
     * @param username the username
     * @return the user details
     * @throws UsernameNotFoundException the username not found exception
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsernameAndIsDeletedFalse(username)
                                       .orElseThrow(() -> new UsernameNotFoundException(BusinessMessage.INCORRECT_USERNAME_PASSWORD));

        return new UserDetailsImpl(user.getId(), username, user.getPassword(), user.getAuthority());
    }
}

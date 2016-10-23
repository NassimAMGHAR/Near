package com.near.friendly.service.user;

import com.near.friendly.core.model.user.User;
import com.near.friendly.core.model.user.UserSession;
import com.near.friendly.repository.UserRepository;
import com.near.friendly.repository.UserSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * User service
 */
@Slf4j
@Service(value = "userDetailsService")
public class UserService implements UserDetailsService {


    @Inject
    private UserSessionRepository userSessionRepository;

    @Inject
    private UserRepository userRepository;


    public void deleteSession(UserSession userSession) {
        userSessionRepository.delete(userSession);
    }

    public Optional<UserSession> getUserSessionById(String id) {
        return userSessionRepository.findOneById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.debug("Authenticating {}", login);

        final String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);

        final Optional<User> userFromDatabase = userRepository.findOneByLogin(lowercaseLogin);

        return userFromDatabase.map(user -> {

            final List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                    .collect(Collectors.toList());

            return new org.springframework.security.core.userdetails.User(lowercaseLogin,
                    user.getPassword(),
                    grantedAuthorities);
        }).orElseThrow(
                () -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
                        "database"));
    }

    public boolean saveUserSession(UserSession userSession) {
        try {
            userSessionRepository.saveAndFlush(userSession);
            return true;
        } catch (DataAccessException e) {
            log.error("Failed to save persistent token ", e);
            return false;
        }
    }

    public Optional<User> getUserByLogin(final String login) {
        return userRepository.findOneByLogin(login);
    }
}

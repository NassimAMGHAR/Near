package com.near.friendly.repository;

import com.near.friendly.core.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneById(Long userId);

    Optional<User> findOneByLogin(String login);

}

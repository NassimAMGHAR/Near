package com.near.friendly.repository;

import com.near.friendly.core.model.user.User;
import com.near.friendly.core.model.user.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    List<UserSession> findByUser(User user);

    Optional<UserSession> findOneById(String id);
}

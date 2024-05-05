package org.kenuki.parkingpro.repos;

import org.kenuki.parkingpro.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNicknameOrEmail(String nickname, String email);
}


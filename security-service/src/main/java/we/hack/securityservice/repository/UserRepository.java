package we.hack.securityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import we.hack.securityservice.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

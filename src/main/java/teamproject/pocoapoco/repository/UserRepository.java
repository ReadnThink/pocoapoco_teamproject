package teamproject.pocoapoco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.pocoapoco.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Login userName 확인
    Optional<User> findByUserName(String userName);
}

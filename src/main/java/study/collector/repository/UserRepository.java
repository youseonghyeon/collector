package study.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.collector.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);
}

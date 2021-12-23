package study.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.collector.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUid(String uid);
}

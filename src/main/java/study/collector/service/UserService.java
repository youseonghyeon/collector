package study.collector.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.User;
import study.collector.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(User user) {
        validateDuplicateUser(user.getUid()); // 아이디 중복 검증
        userRepository.save(user);
        return user.getId();
    }

    // 아이디 중복 검사
    public void validateDuplicateUser(String uid) {
        User findUsers = userRepository.findByUid(uid);
        if (findUsers != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 탈퇴
     */

    @Transactional
    public void remove(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public void removeById(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * 메모 저장
     */

    @Transactional
    public void saveMemo(Long userId, String memo) {
        Optional<User> optionalUser = userRepository.findById(userId);
        try {
            User user = optionalUser.get();
            user.changeMemo(memo);
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(UserService.class);
            logger.error("존재하지 않는 유저입니다. {}", e);
        }
    }

}

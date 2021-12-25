package study.collector.service;

import lombok.RequiredArgsConstructor;
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
     * 회원 삭제
     */

    @Transactional
    public void remove(User user) {
        userRepository.delete(user);
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
            System.out.println("e = " + e);
        }
    }

}

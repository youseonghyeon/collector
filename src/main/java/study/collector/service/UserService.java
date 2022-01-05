package study.collector.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.User;
import study.collector.exception.customexception.UserException;
import study.collector.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * userId로 유저 찾기
     */
    public User searchByLoginId(String loginId) {
        Optional<User> optionalUser = userRepository.findByLoginId(loginId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserException("회원을 찾을 수 없습니다.1");
        }
    }

    public User searchById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserException("회원을 찾을 수 없습니다.2");
        }
    }

    /**
     * 회원가입
     */
    @Transactional
    public Long join(User user) {
        boolean validate = validateDuplLoginId(user.getLoginId());// 아이디 중복 검증
        if (validate) {
            userRepository.save(user);
            return user.getId();
        }
        return null;
    }

    /**
     * 아이디 중복 검사
     */
    public boolean validateDuplLoginId(String loginId) {
        Optional<User> findUsers = userRepository.findByLoginId(loginId);
        if (findUsers.isPresent()) {
            return false;
        }
        return true;
    }

    /**
     * 인
     * 아이디로 회원 탈퇴
     */
    @Transactional
    public void removeById(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * 메모 저장
     */
    @Transactional
    public String saveMemo(Long userId, String newMemo) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            optionalUser.get().changeMemo(newMemo);
            return newMemo;
        }
        return null;
    }

    /**
     * 메모 조회
     */
    public String searchMemo(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get().getMemo();
        }
        return null;
    }

    /**
     * 로그인
     */
    public User login(String loginId, String password) {
        User findUser = searchByLoginId(loginId);
        String findPassword = findUser.getPassword();
        if (findPassword.equals(password)) {
            log.info("로그인 성공");
            return findUser;
        } else {
            return null;
        }
    }

}

package study.collector.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.User;
import study.collector.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    /**
     * 예외처리 공부해야 함
     */
//    @Test
//    public void 회원가입_중복_검사() {
//
//        User user1 = new User("user1", "qwe123");
//        userService.join(user1);
//
//        User user2 = new User("user1", "abc888");
//        userService.join(user2);
//
//        Assertions.assertThrows(IllegalStateException.class, ()->{
//            System.out.println("성공");
//        });
//    }

}

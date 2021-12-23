package study.collector.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.User;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    UserRepository userRepository;
    JPAQueryFactory queryFactory;

    @Test
    public void 회원가입() {
        //given
        User user = createUser("user", "qwe123");

        //when
        User findUser = userRepository.findById(user.getId()).get();

        //then
        assertThat(user).isEqualTo(findUser);
    }

    @Test
    public void 비밀번호_변경() {
        //given
        User user = createUser("user", "qwe123");

        //when
        user.changePassword("abc123");
        em.flush();
        em.clear();

        //then
        User findUser = userRepository.findById(user.getId()).get();
        assertThat(findUser.getPassword()).isEqualTo("abc123");
    }

    private User createUser(String uid, String password) {
        User user = new User(uid, password);
        User savedUser = userRepository.save(user);
        return savedUser;
    }


}

package study.collector.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.Schedule;
import study.collector.entity.User;
import study.collector.repository.ScheduleRepository;
import study.collector.repository.UserRepository;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void init() {
        queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 예외처리 공부해야 함
     */
    @Test
    @Rollback
    public void 회원가입_중복_검사() {
        //given
        User user1 = new User("user1", "qwe123");
        userService.join(user1);

        //when & then
        User user2 = new User("user1", "abc888");
        assertThrows(IllegalStateException.class, () -> {
            userService.join(user2);
        });
    }

    @Test
    public void 회원이_삭제되었을때_북마크_일정도_삭제되어야함() {
        //given
        User user = new User("user", "qwe123", "a@o.com");
        Long userId = userService.join(user);
        Long scheduleId = scheduleService.create(LocalDate.now(), "내용", userId);

        //when
        userService.removeById(userId);
        em.flush();
        em.clear();

        //then
        Optional<Schedule> findSchedule = scheduleRepository.findById(scheduleId);
        assertFalse(findSchedule.isPresent());
    }

}

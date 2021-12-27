package study.collector.service;


import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.Schedule;
import study.collector.entity.User;
import study.collector.repository.ScheduleRepository;
import study.collector.repository.UserRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static study.collector.entity.QSchedule.schedule;

@SpringBootTest
@Transactional
@Commit
class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void init() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void 일정_추가() {
        //given
        User user = createUser("user", "qwe123");

        //when
        scheduleService.create(LocalDate.now(), "내용", user.getId());

        //then
        List<Schedule> result = queryFactory
                .selectFrom(schedule)
                .where(schedule.user.id.eq(user.getId()))
                .fetch();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getContent()).isEqualTo("내용");
    }

    @Test
    public void 일정_삭제() {
        //given
        User user = createUser("user", "qwe123");
        Schedule schedule = createSchedule("내용", user);

        //when
        scheduleRepository.deleteById(schedule.getId());
        em.flush();
        em.clear();

        //then
        User findUser = userRepository.findById(user.getId()).get();

        assertThat(findUser.getSchedules().size()).isEqualTo(0);
    }

    @Test
    public void 일정_조회() {
        //given
        User user = createUser("user", "qwe123");
        createSchedule("내용1", user);
        createSchedule("내용2", user);

        //when
        List<Schedule> schedules = scheduleService.searchById(user.getId());

        //then
        assertThat(schedules.size()).isEqualTo(2);
    }

    public User createUser(String id, String password) {
        User user = new User(id, password);
        em.persist(user);
        return user;
    }

    public Schedule createSchedule(String content, User user) {
        Schedule schedule = new Schedule(LocalDate.now(), content, user);
        em.persist(schedule);
        return schedule;
    }
}

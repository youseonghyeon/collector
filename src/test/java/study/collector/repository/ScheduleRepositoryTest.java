package study.collector.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.Schedule;
import study.collector.entity.User;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
class ScheduleRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    @Test
    public void 회원_아이디로_캘린더_조회() {
        User user = new User("user", "qwe123");
        userRepository.save(user);

        Schedule calendar1 = new Schedule(LocalDateTime.now(), "JPA 수업 듣기", user);
        scheduleRepository.save(calendar1);
        Schedule calendar2 = new Schedule(LocalDateTime.now(), "홍대 미팅", user);
        scheduleRepository.save(calendar2);

        List<Schedule> schedules = scheduleRepository.searchByUserId(user.getId());

        for (Schedule schedule : schedules) {
            System.out.println("schedule = " + schedule);
        }
    }
}

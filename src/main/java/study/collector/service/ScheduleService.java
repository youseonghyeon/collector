package study.collector.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.Schedule;
import study.collector.entity.User;
import study.collector.repository.ScheduleRepository;
import study.collector.repository.UserRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final EntityManager em;


    // 일정 추가
    @Transactional
    public Long create(LocalDate date, String content, Long userId) {
        Optional<User> findUser = userRepository.findById(userId);

        try {
            // 유저가 존재하면
            Schedule schedule = new Schedule(date, content, findUser.get());
            Schedule savedSchedule = scheduleRepository.save(schedule);
            return savedSchedule.getId();
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(ScheduleService.class);
            logger.error("일정 추가 실패");
            throw new EmptyStackException(); // 유저가 없을 때
        }
    }

    //일정 조회
    public List<Schedule> searchById(Long userId) {
        return scheduleRepository.findAllByUserId(userId);
    }

    //일정 삭제
    @Transactional
    public void deleteById(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }
}

package study.collector.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.Schedule;
import study.collector.entity.User;
import study.collector.exception.customexception.ScheduleException;
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
@AllArgsConstructor
public class ScheduleService implements ScheduleServiceInterface{

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final EntityManager em;

    // 일정 추가
    @Transactional
    public Long create(LocalDate date, String content, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Schedule saveSchedule = scheduleRepository.save(new Schedule(date, content, optionalUser.get()));
            return saveSchedule.getId();
        }
        return null;
//      throw new ScheduleException("일정 추가 싪패");
    }

    //일정 조회
    public List<Schedule> search(Long userId) {
        return scheduleRepository.findAllByUserId(userId);
    }

    //일정 삭제
    @Transactional
    public void remove(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    //일정 모두 갱신
    @Override
    public int change(Long userId, List<Schedule> schedules) {
        List<Schedule> findSchedule = scheduleRepository.findAllByUserId(userId);
        scheduleRepository.deleteAllByUserId(userId);
        scheduleRepository.flush();
        em.clear();

        List<Schedule> saveSchedules = scheduleRepository.saveAll(schedules);
        // return 새로운 Schedule 개수
        return saveSchedules.size();
    }

}

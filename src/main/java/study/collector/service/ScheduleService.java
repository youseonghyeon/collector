package study.collector.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.collector.entity.Schedule;
import study.collector.entity.User;
import study.collector.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 일정 추가
    @Transactional
    public void create(LocalDateTime date, String content, User user) {
        Schedule schedule = new Schedule(date, content, user);
        scheduleRepository.save(schedule);
    }

    // 일정 조회

}

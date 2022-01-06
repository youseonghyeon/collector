package study.collector.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.collector.dto.schduledto.CreateScheduleRequest;
import study.collector.dto.schduledto.SearchScheduleResponse;
import study.collector.entity.Schedule;
import study.collector.entity.User;
import study.collector.exception.customexception.ScheduleException;
import study.collector.repository.ScheduleRepository;
import study.collector.repository.UserRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ScheduleService implements ScheduleServiceInterface {

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

    //일정 삭제
    @Transactional
    public void remove(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    //일정 모두 갱신
    @Override
    public int change(Long userId, List<CreateScheduleRequest> createSchedules) {

        scheduleRepository.deleteAllByUserId(userId);


        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            List<Schedule> collect = createSchedules.stream()
                    .map(sc -> new Schedule(LocalDate.parse(sc.getDate()), sc.getContent(), optionalUser.get()))
                    .collect(Collectors.toList());
            for (Schedule schedule : collect) {
                log.info("schedule={}", schedule);
            }
            List<Schedule> saveSchedules = scheduleRepository.saveAllAndFlush(collect);
            return saveSchedules.size();
        }
        return 0;
    }

    public List<SearchScheduleResponse> search(Long userId) {
        List<Schedule> findSchedules = scheduleRepository.findAllByUserId(userId);
        return findSchedules.stream()
                .map(s -> new SearchScheduleResponse(s.getDate(), s.getContent()))
                .collect(Collectors.toList());
    }


}

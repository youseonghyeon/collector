package study.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.collector.entity.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByUserId(Long userId);
}

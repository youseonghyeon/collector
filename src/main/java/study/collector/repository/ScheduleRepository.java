package study.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.collector.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {

}

package study.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.collector.entity.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}

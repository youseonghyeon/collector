package study.collector.repository;

import study.collector.entity.Schedule;

import java.util.List;

public interface ScheduleRepositoryCustom {
    List<Schedule> searchByUserId(Long userId);
}

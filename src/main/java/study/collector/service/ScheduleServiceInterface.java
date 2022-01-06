package study.collector.service;

import study.collector.dto.schduledto.CreateScheduleRequest;
import study.collector.dto.schduledto.SearchScheduleResponse;
import study.collector.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleServiceInterface {

    //삭제
    void remove(Long scheduleId);
    //조회
    List<SearchScheduleResponse> search(Long userId);
    //추가
    Long create(LocalDate date, String content, Long userId);
    //갱신
    int change(Long userId, List<CreateScheduleRequest> schedules);
}

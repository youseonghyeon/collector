package study.collector.controller;

import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.collector.entity.Schedule;
import study.collector.repository.ScheduleRepository;
import study.collector.service.ScheduleService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;

    // 일정 추가
    @PostMapping("/schedule/create")
    public void createSchedule(@RequestBody createScheduleRequest request) {
        scheduleService.create(request.getDate(), request.getContent(), request.getId());
    }

    @Getter
    static class createScheduleRequest {
        private Long id;
        private String content;
        private LocalDate date;
    }


    // 일정 검색
    @PostMapping("/schedule/search")
    public Result getSchedule(@RequestBody GetScheduleRequest request) {
        List<Schedule> schedules = scheduleService.searchById(request.getId());
        List<SimpleScheduleDto> scheduleDto = schedules.stream()
                .map(s -> new SimpleScheduleDto(s.getContent(), s.getDate()))
                .collect(Collectors.toList());

        return new Result(scheduleDto);
    }

    @Data
    static class GetScheduleRequest{
        private Long id;
    }

    @Getter
    static class SimpleScheduleDto {
        private String content;
        private LocalDate date;
        public SimpleScheduleDto(String content, LocalDate date) {
            this.content = content;
            this.date = date;
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }


}

package study.collector.controller;

import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.collector.entity.Schedule;
import study.collector.repository.ScheduleRepository;
import study.collector.service.ScheduleService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;

    // 일정 조회
    @PostMapping("/schedule/search")
    public Result getSchedule(@RequestBody @Valid GetScheduleRequest request) {
        List<Schedule> schedules = scheduleService.searchById(request.getId());
        List<SimpleScheduleDto> simpleScheduleDto = simpleDtoMapping(schedules);

        return new Result(simpleScheduleDto);
    }

    @Data
    static class GetScheduleRequest {
        @NotEmpty
        private Long id;
    }

    // 일정 추가
    @PostMapping("/schedule/create")
    public Result createSchedule(@RequestBody @Valid createScheduleRequest request) {
        scheduleService.create(request.getDate(), request.getContent(), request.getId());

        // 조회
        List<Schedule> schedules = scheduleService.searchById(request.getId());
        List<SimpleScheduleDto> simpleScheduleDto = simpleDtoMapping(schedules);

        return new Result(simpleScheduleDto);
    }

    @Getter
    static class createScheduleRequest {
        @NotEmpty
        private Long id;
        @NotEmpty
        private String content;
        @NotEmpty
        private LocalDate date;
    }

    //일정 삭제
    @PostMapping("/schedule/remove")
    public Result removeSchedule(@RequestBody @Valid removeScheduleRequest request) {
        scheduleService.deleteById(request.getScheduleId());

        //조회
        List<Schedule> schedules = scheduleService.searchById(request.getId());
        List<SimpleScheduleDto> scheduleDtoList = simpleDtoMapping(schedules);
        return new Result(scheduleDtoList);
    }

    @Data
    static class removeScheduleRequest {
        @NotEmpty
        private Long id;
        @NotEmpty
        private Long scheduleId;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    public List<SimpleScheduleDto> simpleDtoMapping(List<Schedule> schedules) {
        return schedules.stream()
                .map(s -> new SimpleScheduleDto(s.getId(), s.getContent(), s.getDate()))
                .collect(Collectors.toList());
    }

    @Getter
    static class SimpleScheduleDto {
        private Long scheduleId;
        private String content;
        private LocalDate date;

        public SimpleScheduleDto(Long scheduleId, String content, LocalDate date) {
            this.scheduleId = scheduleId;
            this.content = content;
            this.date = date;
        }
    }


}

package study.collector.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.collector.dto.schduledto.CreateScheduleRequest;
import study.collector.dto.schduledto.SearchScheduleResponse;
import study.collector.entity.Schedule;
import study.collector.service.ScheduleService;
import study.collector.session.SessionManager;
import study.collector.session.SessionUserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final SessionManager sessionManager;

    // 일정 조회
    @PostMapping("/search")
    public Result getSchedule(HttpServletRequest request) {
        SessionUserDto sessionUser = sessionManager.getUserFromSession(request);
        List<SearchScheduleResponse> scheduleList = scheduleService.search(sessionUser.getId());

        return new Result(scheduleList);
    }

    // 일정 초기화 + 추가
    @PostMapping("/create")
    public Result refreshSchedule(@RequestBody List<CreateScheduleRequest> createRequest, HttpServletRequest request) {
        SessionUserDto sessionUser = sessionManager.getUserFromSession(request);
        scheduleService.change(sessionUser.getId(), createRequest);

        List<SearchScheduleResponse> scheduleList = scheduleService.search(sessionUser.getId());
        return new Result(scheduleList);
    }


    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }


}

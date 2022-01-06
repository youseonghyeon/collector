package study.collector.dto.schduledto;

import lombok.AllArgsConstructor;
import lombok.Data;
import study.collector.entity.Schedule;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SearchScheduleResponse {
    private LocalDate date;
    private String content;
}

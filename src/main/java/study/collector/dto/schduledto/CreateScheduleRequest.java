package study.collector.dto.schduledto;

import lombok.Data;

@Data
public class CreateScheduleRequest {
    private String date;
    private String content;
}

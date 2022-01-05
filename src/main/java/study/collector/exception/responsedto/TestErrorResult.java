package study.collector.exception.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestErrorResult {
    private String code;
    private String message;
}

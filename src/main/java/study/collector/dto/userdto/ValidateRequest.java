package study.collector.dto.userdto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ValidateRequest {
    @NotEmpty
    private String loginId;
}

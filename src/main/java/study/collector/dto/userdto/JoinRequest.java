package study.collector.dto.userdto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class JoinRequest {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
}

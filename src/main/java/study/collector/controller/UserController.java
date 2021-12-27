package study.collector.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.collector.dto.UserDto;
import study.collector.entity.User;
import study.collector.repository.UserRepository;
import study.collector.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/users/new") // 회원가입
    public Long createUser(@RequestBody joinRequest request) {

        User user = new User(request.getId(), request.getPassword(), request.getEmail());
        return userService.join(user);
    }

    @Data
    static class joinRequest {
        @NotEmpty
        private String id;
        @NotEmpty
        private String password;
        @NotEmpty
        private String email;
    }

    @PostMapping("/users/secession") // 아이디 삭제
    public String deleteUser(@RequestBody UserDto userDto) {
        User findUsers = userRepository.findByUid(userDto.getId());
        userService.remove(findUsers);
        return "ok";
    }

    @PostMapping("/users/validate") // 아이디 중복 검증
    public String validateDuplicateUser(@RequestBody @Valid validateUserRequest request) {
        try {
            userService.validateDuplicateUser(request.getId());
            return "성공";
        } catch (Exception e) {
            return "실패";
        }
    }

    @Data
    static class validateUserRequest {
        @NotEmpty(message = "값이 있어야합니다.")
        private String id;
    }

    @PostMapping("/users/memo")
    public String getMemo(@RequestBody memoRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.getUserid());
        return optionalUser.get().getMemo();
    }

    @PostMapping("/users/savememo")
    public void saveMemo(@RequestBody memoRequest request) {
        userService.saveMemo(request.getUserid(), request.getMemo());
    }

    @Data
    static class memoRequest {
        private Long Userid;
        private String memo;
    }


}

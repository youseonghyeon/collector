package study.collector.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.collector.dto.UserDto;
import study.collector.entity.User;
import study.collector.repository.UserRepository;
import study.collector.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/home")
    public String HomePage() {
        return "HOME";
    }

    @PostMapping("/users/new")
    public String createUser(@RequestBody UserDto userDto) {
        User user = new User(userDto.getId(), userDto.getPassword());
        Long userId = userService.join(user);
        return "회원번호 = " + userId;
    }

    @PostMapping("users/secession")
    public String deleteUser(@RequestBody UserDto userDto) {
        User findUsers = userRepository.findByUid(userDto.getId());
        userService.remove(findUsers);
        return "ok";
    }
}

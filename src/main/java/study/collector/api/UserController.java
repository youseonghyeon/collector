package study.collector.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.collector.dto.userdto.*;
import study.collector.entity.User;
import study.collector.exception.customexception.UserException;
import study.collector.repository.UserRepository;
import study.collector.service.UserService;
import study.collector.session.SessionConst;
import study.collector.session.SessionManager;
import study.collector.session.SessionUserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    @PostMapping("/new") // 회원가입
    public String createUser(@Valid @RequestBody JoinRequest request) {
        User user = new User(request.getLoginId(), request.getPassword(), request.getEmail());
        Long join = userService.join(user);
        if (join == null) {
            return "fail";
        }
        return "success";
    }

    @PostMapping("/validate") // 아이디 중복 검증
    public ResponseEntity validateDuplicateUser(@RequestBody @Valid ValidateRequest validateRequest) {
        Map<String, String> entity = new HashMap<>();
        boolean validation = userService.validateDuplLoginId(validateRequest.getLoginId());
        if (validation) {
            entity.put("validation", "true");
        } else {
            entity.put("validation", "false");
        }
        return new ResponseEntity(entity, HttpStatus.OK);
    }


    @PostMapping("/login") // 로그인
    public String login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult, HttpServletRequest request) {
        // 로그인 시도
        log.info("로그인 메서드 실행");
        User loginUser = userService.login(loginRequest.getLoginId(), loginRequest.getPassword());

        if (loginUser == null) {
            //로그인 실패
            throw new UserException("로그인 실패");
        }

        // 로그인 성공 sessionDto 생성
        SessionUserDto sessionUserDto = new SessionUserDto(loginUser.getId(), loginUser.getLoginId());
        // 세션 저장
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, sessionUserDto);

        return "ok";
    }

    @PostMapping("/withdrawal") // 회원 탈퇴
    public ResponseEntity deleteUser(@RequestBody UserDto userDto, HttpServletRequest request) {
        SessionUserDto sessionUser = sessionManager.getUserFromSession(request);
        String sessionLoginId = sessionUser.getLoginId();
        User loginUser = userService.login(sessionLoginId, userDto.getPassword());
        if (loginUser == null) {
            throw new UserException("유저가 존재하지 않습니다.");
        }
        // 탈퇴 성공
        userService.removeById(sessionUser.getId());
        Map<String, String> resEntity = new HashMap<>();
        resEntity.put("withdrawal", "success");
        return new ResponseEntity(resEntity, HttpStatus.OK);
    }

    @PostMapping("/memo") // 메모 받아오기
    public String getMemo(HttpServletRequest request) {
        SessionUserDto sessionUser = sessionManager.getUserFromSession(request);

        return userService.searchMemo(sessionUser.getId());
    }

    @PostMapping("/savememo")
    public String saveMemo(@RequestBody MemoRequest memoRequest, HttpServletRequest request) {
        SessionUserDto user = sessionManager.getUserFromSession(request);
        return userService.saveMemo(user.getId(), memoRequest.getMemo());
    }

}

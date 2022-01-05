package study.collector.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import study.collector.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class SessionManager {

    public SessionUserDto getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        SessionUserDto user = (SessionUserDto)session.getAttribute(SessionConst.LOGIN_USER);

        if (user == null) {
            log.info("유저 조회 실패");
            return null;
        }

        return user;
    }
}

package study.collector.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import study.collector.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("로그인 체크 실행");
        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            // 미인증 사용자
            response.sendRedirect("/login?redirectURL=" + requestURI);
            log.info("로그인 체크 실패");
            return false;
        }

        log.info("로그인 체크 성공");
        return true;
    }
}

package project.chattingService.web.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j //로그를 출력하기 위한 애노테이션이다
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    // 스프링 인터셉터 체인은 다음과 같다.
    // Http 요청 -> WAS -> 필터 -> 서블릿 -> 인터셉터1 -> 인터셉터 2 -> 컨트롤러
    // 컨트롤러 호출 전에 실행되는 메소드이다. 로그인하지 않은 멤버가 채팅방에 접속시 로그인 창으로 리다이렉션 하는 역할을 한다.
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception{
        String requestURI = request.getRequestURI(); //리퀘스트 요청을 보내는 URI를 가져온다. ex) /chatting/chat
        log.info("LoginInterceptor"); //로그 출력
        HttpSession session = request.getSession(false); //세션을 가져온다. 세션이 없을경우 생성하지 않고 null을 반환한다.

        if(session==null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
            //세션이 null이거나 세션의 attribute의 key에 대해 value가 null일 경우
            response.sendRedirect("/login?redirectURL="+requestURI); // 사용자가 입력한 주소가 아닌 로그인 창 주소로 리다이렉션 보낸다.
            //로그인에 성공한 경우 사용자가 기존에 입력했던 주소로 이동한다.
            return false;
        }
        return true;
    }
}

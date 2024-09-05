package project.chattingService.web.websocket;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import project.chattingService.domain.member.Member;
import project.chattingService.web.login.SessionConst;

import java.util.Map;

@Slf4j //로그를 출력하기 위한 애노테이션이다
//httpSession의 값을 WebSocketSession에 넣기 위해 사용할 인터셉터 클래스이다.
public class HttpHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    //Http의 handshake가 실행되기 전에 실행되는 메소드이다.
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        log.info("beforeHandshake"); // 메소드가 실행됬음을 알리는 로그이다.

        if(request instanceof ServletServerHttpRequest){
            // ServerHttpRequest가 ServletSErverHttpRequest의 부모이므로 if문은 성립한다.
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request; //HttpSession을 가져오기 위해 형변환 해준다.
            HttpSession session = servletRequest.getServletRequest().getSession(); // HttpSession을 가져온다.
            Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
            // HttpSession에서 SessionConst.LOGIN_MEMBER를 key로 멤버를 가져온다.
            attributes.put(SessionConst.LOGIN_MEMBER,member);
            // attributes에 문자열 상수와 멤버를 넣는다. attributes는 WebSocketSession의 attributes가 된다.
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}

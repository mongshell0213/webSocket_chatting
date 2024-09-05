package project.chattingService.domain.session;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
//레파지토리임을 의미하는 애노테이션이다. 클래스가 스프링의 bean으로 등록되어 component scan을 통해 자동으로 인식된다.
//채팅방에 접속한 멤버들의 웹소켓 세션을 저장하는 레파지토리이다.
public class SessionRepository {
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    //웹 소켓 세션을 저장할 맵이다. concurrentHashMap은 동시성 문제를 해결해주는 해시맵이다.

    public WebSocketSession save(String id, WebSocketSession session){
        // 멤버가 채팅방에 접속하면, 멤버의 웹소켓 세션을 맵에 저장한다.
        sessions.put(id,session);
        return session;
    }
    public void deleteSession(String id){
        //채팅방에서 나가면 맵에서 세션을 삭제한다.
        sessions.remove(id);
    }

    public Collection<WebSocketSession> sessionList(){
        //세션들로 이루어진 리스트를 반환한다. 리스트를 이용해 메시지를 전송한다.
        return sessions.values();
    }
    SessionRepository(){} //생성자이다. @RequiredArgsConstructor에서 사용된다.
}

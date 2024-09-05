package project.chattingService.web.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import project.chattingService.domain.member.Member;
import project.chattingService.domain.message.MessageRepository;
import project.chattingService.domain.session.SessionRepository;
import project.chattingService.web.login.SessionConst;

import java.io.IOException;
import java.util.Map;
@Component // 컴포넌트 스캔시 bean 객체로 만들어주기 위해 @Component를 설정한다.
@RequiredArgsConstructor // final이 붙은 변수의 생성자를 주입해줌
@Slf4j //로그를 사용하기 위한 애노테이션
public class WebSocketHandler extends TextWebSocketHandler {
    private final MessageRepository messageRepository; //메세지 레파지토리

    private final SessionRepository sessionRepository; //웹소켓 세션 레파지토리

    @Override
    //웹소켓 연결이 된 후 실행되는 메소드이다
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        Member current = (Member) attributes.get(SessionConst.LOGIN_MEMBER); // 웹소켓 세션에서 사용자가 로그인한 멤버객체를 가져온다

        sessionRepository.save(session.getId(),session); //세션 레파지토리에 key는 멤버의 id, value는 웹소켓 세션으로 저장한다
        log.info("afterConnectionEstablished"); //로그를 출력한다

        sessionRepository.sessionList().forEach(s->{ //접속해있는 사용자들에게 접속했음을 전송한다
            try {
                s.sendMessage(new TextMessage(current.getId()+"님이 접속하였습니다.")); //접속하였음을 알리는 메시지를 전송한다.
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        log.info("{} 님이 접속하였습니다.",current.getId()); //로그를 출력한다
        messageRepository.save(current.getId()+"님이 접속하였습니다."); //메시지를 저장한다
    }

    @Override
    //텍스트 메시지를 전송하기 위해 사용되는 메소드이다
    protected void handleTextMessage(WebSocketSession session, TextMessage textmessage) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        Member current = (Member) attributes.get(SessionConst.LOGIN_MEMBER); // 웹소켓 세션에서 사용자가 로그인한 멤버객체를 가져온다

        log.info("{} :{}",current.getId(),textmessage.getPayload()); //사용자가 보낸 메세지를 로그로 출력한다
        sessionRepository.sessionList().forEach(s->{ //사용자가 보낸 메시지를 접속하고있는 모든 유저들에게 전송한다
            try {
                s.sendMessage(new TextMessage(current.getId()+" :"+textmessage.getPayload())); //메시지를 전송한다.
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        messageRepository.save(current.getId()+" :"+textmessage.getPayload()); //메시지를 저장한다
    }

    @Override
    //웹소켓 세션이 종료된 후 실행되는 메소드이다. 사용자가 로그아웃하거나 채팅방을 나가거나 창을 종료했을 때 실행된다.
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Member member = (Member)session.getAttributes().get(SessionConst.LOGIN_MEMBER); // 웹소켓 세션에서 사용자가 로그인한 멤버객체를 가져온다
        session.close(); //세션을 닫는다.
        session.getAttributes().remove(SessionConst.LOGIN_MEMBER); //세션에서 멤버를 삭제한다
        sessionRepository.deleteSession(session.getId()); //세션 레파지토리에서 세션을 삭제한다.
        log.info("{} 접속 종료",member.getId()); //로그를 출력한다
    }
}

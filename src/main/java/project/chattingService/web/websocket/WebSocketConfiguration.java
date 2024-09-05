package project.chattingService.web.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor // final이 붙은 변수의 생성자를 주입해줌
@Configuration // Configuration임을 알리는 애노테이션이다.
@EnableWebSocket //웹소켓을 처리하기위해 사용하는 애노테이션이다.
public class WebSocketConfiguration implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler; //웹소켓 핸들러 객체이다.
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler,"/chatting/chat1") //웹소켓 핸들러와 웹소켓 전송지를 추가한다.
                .addInterceptors(new HttpHandshakeInterceptor()) //인터셉터를 추가한다.
                .setAllowedOrigins("*"); //허용 도메인을 설정한다.
    }
}

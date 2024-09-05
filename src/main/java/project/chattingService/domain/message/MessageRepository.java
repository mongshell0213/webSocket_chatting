package project.chattingService.domain.message;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
//레파지토리임을 의미하는 애노테이션이다. 클래스가 스프링의 bean으로 등록되어 component scan을 통해 자동으로 인식된다.
//채팅방에서 전송한 메시지들이 저장되는 레파지토리이다.
public class MessageRepository {
    private static final List<String> messages = new ArrayList<>(); //메시지를 저장하는 리스트이다.

    public String save(String message){
        //리스트에 메시지를 저장하는 메소드이다.
        messages.add(message);
        return message;
    }
    MessageRepository(){} //생성자이다. 추후 @RequiredArgsConstructor에 의해 호출된다.
}

package project.chattingService.web.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.chattingService.domain.member.Member;
import project.chattingService.domain.member.MemberRepository;
import project.chattingService.web.argumentResolver.Login;

@Slf4j //로그 출력을 위한 애노테이션
@Controller //컨트롤러임을 알리는 애노테이션
@RequiredArgsConstructor //final이 붙은 변수에 생성자를 주입해준다.
public class ChatController {
    private final MemberRepository memberRepository; //회원가입한 멤버가 저장되어있는 레파지토리
    @GetMapping("/chatting/chat") // /chatting/chat으로 Get 요청시 실행되는 메소드
    public String chat(@Login Member member, Model model){ //로그인한 멤버만 chatting/chat 정상접근 가능

        log.info("loginMember={}",member.getId());
        model.addAttribute("member",member); //모델에 로그인한 멤버를 모델에 넣어준다.
        return "chatting/chat"; // 채팅방 html을 반환한다.
    }
}

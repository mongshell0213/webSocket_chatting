package project.chattingService.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.chattingService.domain.member.Member;
import project.chattingService.domain.member.MemberRepository;
import project.chattingService.web.argumentResolver.Login;

@Slf4j //로그를 출력하기 위한 애노테이션이다.
@Controller //컨트롤러임을 의미하는 애노테이션이다.
// 컨트롤러는 사용자의 요청을 처리하고 정해진 뷰에 객체를 넘겨주는 역할을 한다.
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    @GetMapping("/")
    public String home(@Login Member member,Model model){
        log.info("member={}",member);
        if(member==null) //로그인하지 않은 경우
            return "home"; //회원가입과 로그인 메뉴가 있는 페이지로 반환한다.
        else{
            model.addAttribute("member",member); //모델에 로그인한 멤버를 넣는다.
            return "loginHome"; //채팅방에 입장할 수 있는 페이지로 반환한다.
        }
    }

}

package project.chattingService.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.chattingService.domain.member.Member;
import project.chattingService.domain.member.MemberRepository;

@Slf4j //로그를 출력하기 위한 애노테이션이다.
@Controller // 컨트롤러임을 알리는 애노테이션이다. 클래스를 bean 객체로 하여 생성한다.
@RequestMapping("/members/join")
// /members/join으로 get,post 요청시 클래스 내부 @GetMapping, @PostMapping이 설정된 메소드를 실행한다.
@RequiredArgsConstructor // final 변수에 생성자를 주입해준다.
public class MemberController {
    private final MemberRepository memberRepository; //회원가입 멤버가 저장되어있는 레파지토리이다

    @GetMapping
    // /members/join으로 get 요청시 실행되는 메소드이다.
    public String memberJoinForm(Model model){
        model.addAttribute("member",new Member());
        //id와 패스워드가 설정되지않은 멤버 객체를 생성해서 모델에 넣어준다. 에러를 출력하기 위함이다.
        return "members/joinForm"; //회원가입 폼 html을 반환한다.
    }

    @PostMapping
    // /members/join으로 post 요청시 실행되는 메소드이다.
    public String memberJoin(@Validated @ModelAttribute Member member, BindingResult result){
        // @Validated는 객체를 검증하는 역할을 한다. id, password의 @NotBlank 와 @Length를 사용한다.
        if(memberRepository.findById(member.getId()).orElse(null)!=null) //회원가입시 사용한 아이디가 이미 존재하는 아이디일 경우
            result.rejectValue("id","duplicate","이미 있는 아이디입니다.");
        if(result.hasErrors()){ //@NotBlank 또는 @Length에서 에러가 발생한 경우
            log.info("errors={}",result);
            return "members/joinForm"; //회원가입 폼 html을 반환한다
        }
        log.info("member {}",member.getId()); //회원가입한 멤버의 아이디를 출력하는 로그이다.
        memberRepository.save(member); //레파지토리에 회원을 저장한다.
        return "redirect:/"; //홈으로 리다이렉트 한다.
    }
}

package project.chattingService.web.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.chattingService.domain.login.LoginFormat;
import project.chattingService.domain.member.Member;

@Slf4j //로그를 출력하기 위한 애노테이션
@Controller //Controller 임을 알리는 애노테이션
@RequiredArgsConstructor // final이 붙은 변수의 생성자를 주입해줌
public class LoginController {
    private final LoginService loginService;
    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginFormat loginFormat){
        // @ModelAttribute는 HTTP Body와 HTTP 파라미터의 값을 getter, setter, 생성자를 통해 주입하기 위해 사용한다.
        return "login/login-form"; // login-form.html을 반환한다.
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginFormat loginFormat,
                        BindingResult bindingResult, HttpServletRequest request,
                        @RequestParam(defaultValue = "/")String redirectURL){
        Member loginMember = loginService.login(loginFormat.getId(),loginFormat.getPassword());
        //로그인 시 사용한 아이디와 비밀번호를 기반으로 회원가입된 멤버를 찾아온다.

        if(loginMember == null){
            //아이디 또는 비밀번호가 맞지 않아 loginMember가 null인 경우
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
        }
        //에러 발생시 로그인폼 html을 다시 출력한다. (아이디나 패스워드가 공백인 경우)
        if(bindingResult.hasErrors())
            return "login/login-form";

        log.info("loginMember {}",loginMember);

        //로그인 성공
        HttpSession session = request.getSession(); //세션을 가져온다. 없을 경우 생성한다.
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember); //세션에 문자열 상수를 키, 멤버를 밸류로 하여 attribute를 설정한다.
        return "redirect:"+redirectURL; // /으로 리다이렉트 한다.
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false); //세션을 가져온다.
        if(session!=null){
            session.invalidate(); //가져온 세션이 null이 아닐 경우 종료한다.
        }
        return "redirect:/"; // /로 리다이렉트 한다.
    }
}

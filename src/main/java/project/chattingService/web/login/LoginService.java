package project.chattingService.web.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.chattingService.domain.member.Member;
import project.chattingService.domain.member.MemberRepository;

@Service //서비스를 의미하는 애노테이션이다. 루트 컨테이너에 클래스를 bean 객체로 생성해준다.
@RequiredArgsConstructor // final이 붙은 변수의 생성자를 주입해줌
public class LoginService {
    private final MemberRepository memberRepository; //멤버가 저장되어있는 레파지토리 객체이다

    public Member login(String id,String password){
        return memberRepository.findById(id)
                .filter(m->m.getPassword().equals(password))
                .orElse(null);
        //입력한 id를 key로 갖는 멤버를 찾아서 입력한 password를 갖는지 확인한다.
    }
}

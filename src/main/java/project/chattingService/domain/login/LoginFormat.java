package project.chattingService.domain.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter // 변수 setter를 생성해주는 애노테이션
@Getter // 변수 getter를 생성해주는 애노테이션
public class LoginFormat {
    @NotBlank(message="아이디는 공백일 수 없습니다.") //로그인 폼에서 id에 공백 저장시 메시지 출력하면서 회원가입 되지 않음
    private String id;

    @NotBlank(message="비밀번호는 공백일 수 없습니다.") //로그인 폼에서 password에 공백 저장시 메시지 출력하면서 회원가입 되지 않음
    private String password;

    public LoginFormat(String id,String password){ //생성자
        this.id = id;
        this.password = password;
    }
}

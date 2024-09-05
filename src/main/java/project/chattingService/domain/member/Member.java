package project.chattingService.domain.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter // 롬복 애노테이션이다. 클래스의 변수에 대해 getter와 setter를 생성해준다.
public class Member {

    @NotBlank(message="아이디는 공백일 수 없습니다.")
    //아이디에 공백이 들어갈 수 없게 한다. 공백을 넣을 경우 메시지에 저장된 문자열이 출력되고 에러가 발생한다.
    private String id;

    @NotBlank(message="비밀번호는 공백일 수 없습니다.")
    @Length(min=8,message="비밀번호는 최소 8자리 이상이어야 합니다.")
    //아이디에 공백이 들어갈 수 없게 한다. 공백을 넣을 경우 메시지에 저장된 문자열이 출력되고 에러가 발생한다.
    //비밀번호의 길이를 최소 8자리로 한다. 그 미만일 경우 에러가 발생한다.
    private String password;

    public Member(String id, String password){
        this.id = id;
        this.password = password;
    }
    public Member(){

    }
}

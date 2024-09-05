package project.chattingService.web.argumentResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import project.chattingService.domain.member.Member;
import project.chattingService.web.login.SessionConst;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) { //@Login이 있으면서 멤버 타입이면 true 반환
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class); //파라미터가 @Login 애노테이션을 가지고 있는지
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());
        //isAssignableFrom : 파라미터가 Member 타입인지

        return hasLoginAnnotation && hasMemberType;
    }

    @Override
    //컨트롤러 호출 직전에 호출되어 실제 객체 (Member)를 생성한다.
    //컨트롤러 호출 시 객체가 넘어가게 된다.
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer
            , NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request =(HttpServletRequest)webRequest.getNativeRequest();
        HttpSession session = request.getSession(false); //HttpSession 을 가져온다.
        log.info("argument resolver session={}",session); //세션 로그 출력
        if(session==null) //세션이 없을 경우 멤버가 아닌 null을 반환한다.
            return null;
        return session.getAttribute(SessionConst.LOGIN_MEMBER); // SessionConst.LOGIN_MEMBER를 key로 하여 멤버를 반환한다.
    }
}

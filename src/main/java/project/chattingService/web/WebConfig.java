package project.chattingService.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.chattingService.web.argumentResolver.LoginMemberArgumentResolver;
import project.chattingService.web.login.LoginInterceptor;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()) //로그인 인터셉터를 추가한다. 로그인하지 않은 사용자가 허용되지 않은 주소에 접근하는 것을 막는다.
                .order(1) //우선순위를 설정한다.
                .addPathPatterns("/**") //기본적으로 모든 경로에 대해 인터셉터가 실행되게 한다.
                .excludePathPatterns("/","/login","/members/join");
                // 홈(/), 로그인 페이지(/login), 회원가입 페이지(/members/join)에 대해서는 인터셉터가 동작하지 않도록 한다.

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver()); //argumentResolver를 추가한다.
    }
}

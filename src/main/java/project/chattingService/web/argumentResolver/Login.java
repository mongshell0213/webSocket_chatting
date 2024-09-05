package project.chattingService.web.argumentResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //사용할 애노테이션을 적용할 대상을 지정하기위해 사용한다. 파라미터를 지정한다.
@Retention(RetentionPolicy.RUNTIME) // 리플렉션등을 활용할 수 있도록 런타임까지 애노테이션 정보가 남아있게 한다.
public @interface Login {
}

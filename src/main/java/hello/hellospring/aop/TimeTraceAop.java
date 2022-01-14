package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect //aop를 사용하기위한 어노테이션
@Component // 이 클래스는 스프링 빈에 등록해야됨 그래서 이렇게 컴포넌트 스캔하게 어노테이션 붙여도 되고
//SpringConfig에 따로 스프링 빈으로 등록해줘도 됨 여기선 어노테이션으로 등록해줄거임
public class TimeTraceAop {
    // aop를 사용하면 서비스 로직 코드를 건드리지 않아도 메소드 시간측정처럼 코드 안에 들어가야하는 다른 관점의 로직들을 깔끔하게 관리가 가능
    //각 서비스에 각각 넣어줘야했던 시간측정 로직을 aop를 사용해서 한번에 적용 시킬 수 있음
    @Around("execution(* hello.hellospring..*(..))")//이 로직을 어느부분에 적용시킬건지 세팅하는 어노테이션
    //hello.hellospring 패키지 하위엔 전부 적용시키겠다
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable
    {   //일반 서비스에서 메소드 호출을 할때마다 이 메소드가 호출되는것 그래서 @Around()에 적어주면 각 서비스마다 시간측정이 가능한것
        long start = System.currentTimeMillis(); //회원가입 시간 측정
        System.out.println("Start: "+ joinPoint.toString());
        try {
            Object result = joinPoint.proceed();//일반서비스에서 이 aop 코드로 인터셉트되었으니 다음 일반 메소드를 호출해줘야 서비스가 실행됨 다음 메소드를 호출하는게 joinPoint.proceed()
            return result;
        } finally {
            long finish = System.currentTimeMillis(); //회원가입 시간 측정
            long timeMs = finish - start;
            System.out.println("END = " + joinPoint.toString() + " " + timeMs + "ms");
        }

    }
}

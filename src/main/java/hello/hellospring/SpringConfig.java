package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 이 파일은 자바코드로 스프링 빈을 직접 등록하는 설정을 위한 파일

@Configuration
public class SpringConfig {


    @Bean //스프링이 뜰때 이 파일을 읽고 @Bean인 메서드들을 실행시켜고 구현된 로직을 통해 스프링빈에 등록함
    public MemberService memberService()
    {
        return new MemberService(memberRepository());
        //memberRepository()로 밑에서 스프링빈에 등록한 MemberRepository를 여기에 넣어주는거
        //MemberService의 생성자를 MemberRepository가 있어야된다고 만들어서 밑에 MemberRepository도 만듬
    }

    @Bean
    public MemberRepository memberRepository()
    {
        return new MemoryMemberRepository(); //MemberRepository는 인터페이스라 구현체인 MemoryMemberRepository만듬
    }
}

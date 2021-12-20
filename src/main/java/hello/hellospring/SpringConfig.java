package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

// 이 파일은 자바코드로 스프링 빈을 직접 등록하는 설정을 위한 파일
// 원래는 그냥 @Service @Repository로 자동으로 di 해주게 하지만 강의중 자바코드로 직접 스프링 빈을 등록하는 과정이 있어서 이렇게 해봤음

@Configuration
public class SpringConfig {

//    순수jdbc코드  JdbcTemplate코드 에서 사용
//    private DataSource dataSource;
//    //스프링이 application.properties를 보고 자동으로 datasource 만들어줌 그걸 생성자를 통해 di 해주는것
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

//jpa의 entitymanager 코드로 spring data jpa는 구현체를 자동으로 만들어주고 등록해서 필요없음
//   private EntityManager em;
//    @Autowired //jpa의 entity manager di
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    private final MemberRepository memberRepository;
    @Autowired //springdatajpa가 자동으로 만들어놓은 구현체가 주입됨
    public SpringConfig(MemberRepository memberRepository) { //JpaRepository와 MemberRepository를 상속받은 인터페이스를 SpringDataJpaMemberRepository에서 만들어놓으면 인터페이스에 대한 구현체를 만들어서 그걸 주입해줌
        this.memberRepository = memberRepository;
    }

    @Bean //스프링이 뜰때 이 파일을 읽고 @Bean인 메서드들을 실행시켜고 구현된 로직을 통해 스프링빈에 등록함
    public MemberService memberService()
    {
        return new MemberService(memberRepository);

        //return new MemberService(memberRepository()); // springdatajpa에서는 구현체가 자동으로 만들어지기에 주입받은 변수로 서비스 의존관계를 변경 그래서 이건 안씀
        //memberRepository()로 밑에서 스프링빈에 등록한 MemberRepository를 여기에 넣어주는거
        //MemberService의 생성자를 MemberRepository가 있어야된다고 만들어서 밑에 MemberRepository도 만듬
    }

//    @Bean
//    public MemberRepository memberRepository() //db연결 코드로 springdatajpa에선 사용하지 않음
//    {
//        //return new MemoryMemberRepository(); //메모리db코드, MemberRepository는 인터페이스라 구현체인 MemoryMemberRepository만듬
//        //return new JdbcMemberRepository(dataSource); //순수jdbc코드, 메모리로 사용하던 리포지토리를 db로 교체
//        //return new JdbcTemplateMemberRepository(dataSource);//JdbcTemplate코드
//        //return new JpaMemberRepository(em); //jpa 코드
//
//    }
}

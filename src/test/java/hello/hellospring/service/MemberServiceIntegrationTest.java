package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

//이건 통합테스트 (스프링, db등 다 붙여서 하는 테스트로 시간이 오래걸림)

@SpringBootTest // 스프링 테스트를 위한 어노테이션
@Transactional //테스트 케이스에 이 어노테이션이 있으면 테스트 시작 전 트랜잭션을 시작해 각 테스트가 끝난후 commit하지 않고 rollback함, 이러면 db에 데이터가 남지 않으니 다음 테스트에 영향을 주지 않음(테스트는 반복이 되어야하는데 테스트 내용이 db에 저장되면 반복이 안될수도 있으니 이걸 붙임)
class MemberServiceIntegrationTest {

    @Autowired //테스트 용도이기에 생성자에 autowired 하지 않고 그냥 간단히 변수에 직접 DI
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() { //테스트코드는 한글로 적어도 됨 테스트코드는 빌드될때 코드에 포함되지 않음
        //given     given when then 방식으로 주어진걸로 무엇을 할때 어떤게 나오는가 라는 그냥 코드 나열 방식
        Member member = new Member(); //id는 이때 sequence변수로 자동생성
        member.setName("spring100");

        //when
        Long saveId = memberService.join(member); //회원가입 신청

        //then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());// 잘들어갔는지 테스트
    }

    @Test
    public void 중복_회원가입_예외()
    {
        //given 중복회원 생성
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when 회원가입 수행 여기서 예외가 터져줘야함
        memberService.join(member1);
        //try catch말고 간단한 문법으로 처리가 되는데 assertThrows(오류1, 이 람다식을 실행했을때오류1이 터진다) 이런식
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 이름의 회원입니다");


    }

}
package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach()
    {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }


    @AfterEach // 각각의 테스트 메서드가 끝날때마다 이 동작을 함
    public void afterEach()
    {
        memberRepository.clearStore(); // 클래스 단위로 테스트할때 메서드마다 객체 생성되어 같은 value 겹치면 오류남 저장소 비우기
    }

    @Test
    void 회원가입() { //테스트코드는 한글로 적어도 됨 테스트코드는 빌드될때 코드에 포함되지 않음
        //given     given when then 방식으로 주어진걸로 무엇을 할때 어떤게 나오는가 라는 그냥 코드 나열 방식
        Member member = new Member(); //id는 이때 sequence변수로 자동생성
        member.setName("spring");

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


//        try
//        {
//            memberService.join(member2); //여기서 join 함수에 만들어놓은 중복시 에러를 생성하는게 터질것
//            fail();//오류발생안하고 여기로 내려오면 테스트 fail
//        }
//        catch (IllegalStateException e) //그럼 여기서 catch해줌 join에선 오류를 만들기만하니 catch를 따로 해야됨
//        {
//            //에러가 발생해서 에러 메세지를 비교하고 서로 같으면 정상적으로 테스트된것
//            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 이름의 회원입니다");
//        }

        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}
package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//spring data jpa에서 jpaRepository를 상속받은 이걸 보고 스프링 빈을 자동으로 만들어줌 우린 그걸 주입해서 사용하는것
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    //camel 표기법을 준수하여 특정 규칙을 통해 추상메소드를 선언해놓으면 jpa가 알아서 jpql을 짜주어서 메소드 이름만으로 조회 기능 개발을 끝낼수있음
    @Override
    //이건 JPQL로는 select m from Member m where m.name = ? 이게 될것
    Optional<Member> findByName(String name); //Member의 필드인 name으로 찾고싶다


}

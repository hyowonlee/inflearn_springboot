package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

//원래는 db를 사용하지만 여기선 간단한프로젝트로 아직 db가 정해지지 않았다고 가정하고 수행 그래서 메모리에 저장

public interface MemberRepository {
        Member save(Member member);
        Optional<Member> findById(Long id);
        Optional<Member> findByName(String name); //Optional은 자바8에 들어간 기능인데 이 함수들의 리턴값이 null일경우 이 optional로 감싸서 반환되게됨
        List<Member> findAll();
}

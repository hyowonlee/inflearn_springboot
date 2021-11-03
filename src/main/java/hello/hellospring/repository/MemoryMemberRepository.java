package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//원래는 db를 사용하지만 여기선 간단한프로젝트로 아직 db가 정해지지 않았다고 가정하고 수행 그래서 메모리에 저장

//@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; //늘어나는숫자 key값 생성용

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // 멤버 세이브할때 시퀀스 값 1개씩 늘리기
        store.put(member.getId(), member); //map에 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        //이렇게 optional로 반환을 해주면 null을 감싸서 반환이되는데 이러면 null이 반환되도 클라이언트에서 뭘 할 수 있다
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();// 값 돌면서 찾은 요소 중 아무거나 하나 반환 findFirst()는 첫번째 하나 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore()
    {
        store.clear();
    }
}

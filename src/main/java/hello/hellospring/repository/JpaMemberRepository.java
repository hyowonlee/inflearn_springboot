package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    //build.gradle의 starter-data-jpa라이브러리에서 자동으로 EntityManager를 생성해서 주입
    private final EntityManager em; //jpa에서는 이 EntityManager라는것으로 모든걸 동작함

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // 이 한줄이면 jpa가 다해줌
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);//id로 조회 (조회할타입, id)
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class) //jpql이란 쿼리언어로 객체를 대상으로 query를 날리는언어로 sql로 번역된다
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)  //jpql이란 쿼리언어로 객체를 대상으로 query를 날리는언어로 sql로 번역된다
                .getResultList();
    }
}

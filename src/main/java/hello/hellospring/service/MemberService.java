package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service
@Transactional //jpa를 사용할땐 주의해야될게 데이터를 저장하거나 변경시 항상 트랜잭션이 있어야함
public class MemberService {

    private final MemberRepository memberRepository;

    //@Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    //회원가입
    public Long join(Member member)
    {
        //같은 이름이 있는 중복 회원은 안됨, 중복회원 검증
        validateDuplicateMember(member); //바로 밑에 구현됨

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { //중복이름일시 오류로 넘김
            throw new IllegalStateException("이미 존재하는 이름의 회원입니다");
        });
    }

    //전체 회원 조회
    public List<Member> findMembers()
    {
        return memberRepository.findAll();
    }
    
    //회원 한명 조회
    public Optional<Member> findOne(Long memberId)
    {
        return memberRepository.findById(memberId);
    }
}

package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // 컴포넌트 어노테이션 포함 -> 빈 등록 대상
@RequiredArgsConstructor // final 키워드 붙은 필드가진 생성자 만들어줌
@Transactional // JPA의 모든 데이터 변경이나 로직은 Transaction 안에서 수행되어야함.
public class MemberService {

    // @RequiredArgsConstructor 어노테이션으로 의존성 주입 -> 생성자 주입 방식
    private final MemberRepository memberRepository;

    // 회원 가입
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty())
            throw new IllegalStateException("이미 존자하는 회원입니다.");
    }

    @Transactional(readOnly = true) // read only transaction
    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 하나 조회
    @Transactional(readOnly = true) // read only transaction
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

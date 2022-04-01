package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // 컴포넌트 어노테이션 포함 -> 빈 등록 대상
@RequiredArgsConstructor
public class MemberRepository {

    // EntityManager를 주입하는 방법
    // 1. @PersistenceContext 어노테이션을 이용하기
        // @PersistenceContext
        // private EntityManager em;

    // 2. 생성자를 만들어주는 어노테이션을 사용해 주입하기. -> 스프링 부트가 em도 편리하게 주입할 수 있도록 기능 제공
        // 1) em을 final로 등록하고
        // 2) @RequiredArgsConstructor을 사용하면 -> final 키워드로 된 필드를 가지고 생성자를 만들어줌
        // 3) 생성자 주입과 같은 효과
    private final EntityManager em;

    // 저장
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    // 하나 조회
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    // 전체 조회
    // JPQL을 사용 -> 테이블이 아닌 엔티티를 대상으로
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 이름으로 찾기
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}

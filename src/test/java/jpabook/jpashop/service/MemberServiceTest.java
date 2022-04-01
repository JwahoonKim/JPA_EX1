package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 스프링 부트 띄우고 테스트 -> Autowired 사용 가능
@Transactional // 테스트 실행마다 트랙잭션을 시작하고 테스트가 끝나면 롤백
//@Rollback(false) // 롤백 하기 싫으면 이 어노테이션을!
class MemberServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;

    @Test
    public void 회원가입() {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        assertThat(member).isEqualTo(memberRepository.findById(saveId));
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("kim");
        member2.setName("kim");

        memberService.join(member1);

        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
    }
}
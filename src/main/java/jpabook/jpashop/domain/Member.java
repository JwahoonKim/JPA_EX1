package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue // 자동 값 생성
    @Column(name = "member_id")
    private Long id;

    private String name;

    // 임베디드 타입을 쓰겠다.
    // Address쪽에서 Embeddable을 써줘서 생략 가능
    @Embedded
    private Address address;

    // 연관관계의 주인이 아니다 -> mappedBy
    // 관계의 주인은 order 쪽
    // member? -> Order 테이블에 있는 member필드에 의해 매핑.
    // orders에 데이터를 추가해준다고 디비에 반영 X
    // -> 즉, 관계의 주인쪽에(외래키를 들고 있는) 추가로 데이터를 넣어주어야 DB에 반영됨.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}

package jpabook.jpashop.domain;

import jpabook.jpashop.domain.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 테이블 네임을 직접 명시
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 외래키를 들고있는 쪽
    @JoinColumn(name = "member_id") // 외래키인 member_id와 객체를 매핑
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING) // Enum 타입을 쓸 것이고, EnumType에는 String을 써야 안전하다. ORDINAL을 쓰면 변경 시 위험.
    private OrderStatus orderStatus; // 주문 상태 [ORDER, CANCEL]

    // Date 클래스와는 다르게 hibernate가 날짜타입으로 알아서 매핑해줌. Date타입은 구린점이 많다..
    private LocalDateTime orderDate; // 주문 시간
}

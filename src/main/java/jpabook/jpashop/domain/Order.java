package jpabook.jpashop.domain;

import jpabook.jpashop.domain.enums.DeliveryStatus;
import jpabook.jpashop.domain.enums.OrderStatus;
import jpabook.jpashop.exception.AlreadyCompletedItemCancelException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "orders") // 테이블 네임을 직접 명시
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 만들기, access -> 접근제어자 조정
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 외래키를 들고있는 쪽
    @JoinColumn(name = "member_id") // 외래키인 member_id와 객체를 매핑
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING) // Enum 타입을 쓸 것이고, EnumType에는 String을 써야 안전하다. ORDINAL을 쓰면 변경 시 위험.
    private OrderStatus orderStatus; // 주문 상태 [ORDER, CANCEL]

    // Date 클래스와는 다르게 hibernate가 날짜타입으로 알아서 매핑해줌. Date타입은 구린점이 많다..
    private LocalDateTime orderDate; // 주문 시간

    //== 연관관계 메서드 ==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성 메서드 ==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... OrderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        Arrays.stream(OrderItems).forEach(order::addOrderItem);
        return order;
    }

    //== 비지니스 로직 ==//
    public void cancel() {
        if (delivery.getDeliveryStatus() == DeliveryStatus.COMP) {
            throw new AlreadyCompletedItemCancelException();
        }

        this.setOrderStatus(OrderStatus.CANCLE);

        orderItems.forEach(OrderItem::cancle);
    }

    public int getTotalPrice() {
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }
}

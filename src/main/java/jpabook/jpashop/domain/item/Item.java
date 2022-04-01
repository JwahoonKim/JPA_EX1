package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
// 상속 관계로 만들 예정
// -> 상속 관계 전략을 지정해주어야함.
// -> 싱글 테이블 전략을 사용할 것이다.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    //== 비지니스 로직 ==//
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int resStock = this.stockQuantity - quantity;
        if (resStock < 0) throw new NotEnoughStockException("need more stock");
        this.stockQuantity = resStock;
    }
}

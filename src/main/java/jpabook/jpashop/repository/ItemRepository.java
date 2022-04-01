package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // 저장
    public void save(Item item) {
        if (item.getId() == null)
            em.persist(item);
        else
            em.merge(item); // 수정 목적으로 온 save
        // 수정 목적으로 인자에 들어온 item은 영속 컨텍스트에 존재 X -> 준영속 엔티티
        // 따라서 이를 영속 컨텍스트로 넘겨준 뒤 변경해야하는데.. 두가지 방법존재
            // 1) em.find(item.getId()) 를 사용해 다시 조회한 후 데이터를 수정 -> Dirty Checking 할 수 있게 됨
            // 2) merge를 사용하여 영속 컨텍스트에 집어넣는다.
        // but! merge 기능은 위험함. -> why? -> 모든 속성이 변경되기 때문에 병합시 값이 없으면 null 위험 존재.
    }

    // 하나 조회
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    // 전부 조회
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}

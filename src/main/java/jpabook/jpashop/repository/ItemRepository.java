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

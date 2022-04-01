package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    // 저장
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 하나 조회
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    // 전부 조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}

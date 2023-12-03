package jpabook.jpashop.web.item;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;

@Getter
public class ItemResponse {
    private String name;
    private Long price;
    private Integer stockQuantity;

    public ItemResponse(Item item) {
        this.name = item.getName();
        this.price = item.getPrice().getValue();
        this.stockQuantity = item.getStockQuantity().getValue();
    }
}

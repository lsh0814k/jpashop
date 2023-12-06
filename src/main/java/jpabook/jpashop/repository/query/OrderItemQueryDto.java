package jpabook.jpashop.repository.query;

import jpabook.jpashop.domain.Money;
import jpabook.jpashop.domain.Quantity;
import lombok.Builder;
import lombok.Data;

@Data
public class OrderItemQueryDto {
    private Long orderId;
    private String itemName;
    private Money orderPrice;
    private Quantity count;

    public OrderItemQueryDto(Long orderId, String itemName, Money orderPrice, Quantity count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}

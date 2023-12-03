package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.converter.MoneyConverter;
import jpabook.jpashop.domain.converter.QuantityConverter;
import jpabook.jpashop.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@NoArgsConstructor(access = PROTECTED)
public class OrderItem {
    @Builder
    private OrderItem(Money orderPrice, Quantity count, Order order, Item item) {
        this.orderPrice = orderPrice;
        this.count = count;
        this.order = order;
        this.item = item;
    }

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @Convert(converter = MoneyConverter.class)
    private Money orderPrice;

    @Convert(converter = QuantityConverter.class)
    private Quantity count;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public Money getTotalPrice() {
        return orderPrice.multiple(count.getValue());
    }

    public void cancel() {
        item.addStock(count.getValue());
    }

    public static OrderItem createOrderItem(Item item, Long price, Integer count) {
        OrderItem orderItem = OrderItem
                .builder()
                .count(Quantity.of(count))
                .orderPrice(Money.of(price))
                .item(item)
                .build();
        item.removeStock(count);

        return orderItem;
    }
}

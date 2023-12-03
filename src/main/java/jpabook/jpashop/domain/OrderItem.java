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
    public OrderItem(Money orderPrice, Quantity count) {
        this.orderPrice = orderPrice;
        this.count = count;
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
}

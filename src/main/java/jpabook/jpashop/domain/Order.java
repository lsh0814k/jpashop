package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.common.BaseEntity;
import jpabook.jpashop.domain.converter.MoneyConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {
    @Builder
    private Order(OrderStatus status, Money totalPrice, Member member, Delivery delivery) {
        this.status = status;
        this.totalPrice = totalPrice;
        this.member = member;
        this.delivery = delivery;
    }

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @Column(updatable = false)
    private LocalDateTime orderDate;
    @Enumerated(STRING)
    private OrderStatus status;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "total_price")
    private Money totalPrice;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @PrePersist
    private void prePersist() {
        orderDate = LocalDateTime.now();
    }

    public static Order createOrder(Member member, Delivery delivery, List<OrderItem> orderItems) {
        Order order = Order.builder()
                .member(member)
                .delivery(delivery)
                .status(OrderStatus.ORDER)
                .totalPrice(Money.of(orderItems.stream().mapToLong(i -> i.getTotalPrice().getValue()).sum()))
                .build();

        for (OrderItem orderItem: orderItems) {
            order.getOrderItems().add(orderItem);
        }

        return order;
    }

    public void cancel() {
        delivery.checkCancelable();

        this.status = OrderStatus.CANCEL;
        for (OrderItem orderItem: orderItems) {
            orderItem.cancel();
        }
    }

}

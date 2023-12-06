package jpabook.jpashop.repository.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Money;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.Quantity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderFlatDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private Address address;
    private OrderStatus status;

    private String itemName;
    private Money orderPrice;
    private Quantity count;

    public OrderFlatDto(Long orderId, String name, LocalDateTime orderDate, Address address, OrderStatus status, String itemName, Money orderPrice, Quantity count) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.address = address;
        this.status = status;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}

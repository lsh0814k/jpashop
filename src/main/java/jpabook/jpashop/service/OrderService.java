package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberJpaRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberJpaRepository.findById(memberId);
        Item item = itemRepository.findById(itemId);

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice().getValue(), count);
        Delivery delivery = Delivery.builder()
                        .status(DeliveryStatus.READY)
                        .address(member.getAddress())
                        .build();
        Order order = Order.createOrder(member, delivery, Arrays.asList(orderItem));
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId);
        order.cancel();
    }
}

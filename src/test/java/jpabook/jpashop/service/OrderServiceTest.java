package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Slf4j
@SpringBootTest
class OrderServiceTest {
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("상품 주문")
    void order() {
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order order = orderRepository.findById(orderId);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(order.getOrderItems().size()).isEqualTo(1);
        assertThat(order.getTotalPrice()).isEqualTo(Money.of(10000 * 2L));
        assertThat(item.getStockQuantity()).isEqualTo(Quantity.of(8));
    }

    @Test
    @DisplayName("재고수량 초과")
    void notEnoughStockException() {
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 11;
        //expected
        Assertions.assertThatThrownBy(() -> orderService.order(member.getId(), item.getId(), orderCount))
                .isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    @DisplayName("주문 취소")
    void cancel() {
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        orderService.cancelOrder(orderId);

        //when
        Order order = orderRepository.findById(orderId);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity().getValue()).isEqualTo(10);
    }

    @Test
    @DisplayName("전체 주문 조회")
    void findAll() {
        //given
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        List<Order> orders = orderService.findAll(new OrderSearch("회원", OrderStatus.ORDER));
        assertThat(orders.get(0).getMember().getName()).isEqualTo("회원");
        assertThat(orders.get(0).getStatus()).isEqualTo(OrderStatus.ORDER);

    }

    private Member createMember() {
        Member member = Member.builder()
                .name("회원")
                .address(Address.builder()
                        .city("서울")
                        .street("한강")
                        .zipcode("15132")
                        .build())
                .build();
        em.persist(member);

        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = Book.builder()
                .price(Money.of(Long.valueOf(price)))
                .name(name)
                .stockQuantity(Quantity.of(stockQuantity))
                .build();
        em.persist(book);

        return book;
    }
}
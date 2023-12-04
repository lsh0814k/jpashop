package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit1() {
            Member member = createMember("userA", "서울", "한강", "12345");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000L, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000L, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000L, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000L, 2);
            Order order = Order.createOrder(member, createDelivery(member), Arrays.asList(orderItem1, orderItem2));
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("userB", "안산", "화정로", "12341");
            em.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 20000L, 100);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 40000L, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000L, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000L, 4);
            Order order = Order.createOrder(member, createDelivery(member), Arrays.asList(orderItem1, orderItem2));
            em.persist(order);
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = Member.builder()
                    .name(name)
                    .address(Address.builder()
                            .city(city)
                            .street(street)
                            .zipcode(zipcode)
                            .build())
                    .build();

            return member;
        }

        private Book createBook(String name, Long price, int stockQuantity) {
            Book book = Book.builder()
                    .name(name)
                    .price(Money.of(price))
                    .stockQuantity(Quantity.of(stockQuantity))
                    .build();
            return book;
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = Delivery.builder()
                    .address(member.getAddress())
                    .status(DeliveryStatus.READY)
                    .build();

            return delivery;
        }
    }
}

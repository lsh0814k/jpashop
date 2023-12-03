package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Money;
import jpabook.jpashop.domain.Quantity;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ItemServiceTest {
    @Autowired private ItemService itemService;
    @Autowired private EntityManager em;

    @Test
    @DisplayName("상품 등록")
    void saveItem() {
        // given
        Item item = Movie.builder()
                .actor("actor")
                .name("name")
                .director("director")
                .stockQuantity(Quantity.of(10))
                .price(Money.of(10000L))
                .build();
        //when
        itemService.saveItem(item);

        //then
        assertThat(item).isEqualTo(em.find(Item.class, item.getId()));
    }

    @Test
    @DisplayName("수량 예외")
    void notEnoughStockException() {
        // given
        Item item = Movie.builder()
                .actor("actor")
                .name("name")
                .director("director")
                .stockQuantity(Quantity.of(10))
                .price(Money.of(10000L))
                .build();

        //expected
        assertThatThrownBy(() -> item.removeStock(11))
                .isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    @DisplayName("수량 추가")
    void addStockQuantity() {
        //given
        Item item = Movie.builder()
                .actor("actor")
                .name("name")
                .director("director")
                .stockQuantity(Quantity.of(10))
                .price(Money.of(10000L))
                .build();

        //when
        item.addStock(10);

        //then
        assertThat(item.getStockQuantity().getValue()).isEqualTo(20);
    }

    @Test
    @DisplayName("수량 빼기")
    void removeStockQuantity() {
        //given
        Item item = Movie.builder()
                .actor("actor")
                .name("name")
                .director("director")
                .stockQuantity(Quantity.of(10))
                .price(Money.of(10000L))
                .build();

        //when
        item.removeStock(10);

        //then
        assertThat(item.getStockQuantity().getValue()).isEqualTo(0);
    }

    @Test
    @DisplayName("book 수정")
    void bookUpdate() {
        // given
        Item item = Book.builder()
                .name("name")
                .author("author")
                .isbn("isbn")
                .stockQuantity(Quantity.of(10))
                .price(Money.of(10000L))
                .build();

        itemService.saveItem(item);

        //when
        Book modified = Book.builder()
                .name("name")
                .author("author")
                .isbn("isbn")
                .stockQuantity(Quantity.of(12))
                .price(Money.of(12000L))
                .build();

        itemService.updateBook(item.getId(), modified);

        em.flush();
        em.clear();

        //then
        Book findBook = em.find(Book.class, item.getId());
        assertThat(findBook.getStockQuantity()).isEqualTo(Quantity.of(12));
        assertThat(findBook.getPrice()).isEqualTo(Money.of(12000L));
    }
}
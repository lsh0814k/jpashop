package jpabook.jpashop.web.item;

import jpabook.jpashop.domain.Money;
import jpabook.jpashop.domain.Quantity;
import jpabook.jpashop.domain.item.Book;
import lombok.Getter;

@Getter
public class BookForm {
    private Long id;
    private String name;
    private Long price;
    private Integer stockQuantity;

    private String author;
    private String isbn;

    public Book createBook() {
        return Book.builder()
                .name(name)
                .price(Money.of(Long.valueOf(price)))
                .stockQuantity(Quantity.of(stockQuantity))
                .author(author)
                .isbn(isbn)
                .build();
    }
}

package jpabook.jpashop.web.item;

import jpabook.jpashop.domain.item.Book;
import lombok.Getter;

@Getter
public class BookResponse {
    public BookResponse(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.price = book.getPrice().getValue();
        this.stockQuantity = book.getStockQuantity().getValue();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
    }

    private Long id;
    private String name;
    private Long price;
    private Integer stockQuantity;

    private String author;
    private String isbn;
}

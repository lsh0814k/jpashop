package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.domain.Money;
import jpabook.jpashop.domain.Quantity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity @DiscriminatorValue("book")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Book extends Item {
    @Builder
    public Book(String name, Money price, Quantity stockQuantity, String author, String isbn) {
        super(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    private String author;
    private String isbn;

    public void update(Book book) {
        this.name = book.getName();
        this.price = book.getPrice();
        this.stockQuantity = book.getStockQuantity();
        this.author = book.author;
        this.isbn = book.getIsbn();
    }
}

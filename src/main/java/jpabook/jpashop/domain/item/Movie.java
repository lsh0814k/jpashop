package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.domain.Money;
import jpabook.jpashop.domain.Quantity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@DiscriminatorValue("movie")
@NoArgsConstructor(access = PROTECTED)
public class Movie extends Item {
    @Builder
    public Movie(String name, Money price, Quantity stockQuantity, String director, String actor) {
        super(name, price, stockQuantity);
        this.director = director;
        this.actor = actor;
    }

    private String director;
    private String actor;
}

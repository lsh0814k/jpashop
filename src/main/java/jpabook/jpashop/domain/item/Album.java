package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.domain.Money;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue(value = "album")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Album extends Item {
    @Builder
    public Album(String name, Money price, Integer stockQuantity, String artist, String etc) {
        super(name, price, stockQuantity);
        this.artist = artist;
        this.etc = etc;
    }

    private String artist;
    private String etc;
}

package jpabook.jpashop.domain;


import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class Quantity {
    private int value;

    private Quantity(int value) {
        this.value = value;
    }

    public static Quantity of(int value) {
        return new Quantity(value);
    }

    public Quantity add(int value) {
        return new Quantity(this.value + value);
    }

    public Quantity minus(int value) {
        int rest = this.value - value;
        if (rest < 0) {
            throw new NotEnoughStockException("need more stock");
        }

        return new Quantity(rest);
    }
}

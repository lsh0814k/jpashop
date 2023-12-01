package jpabook.jpashop.domain;

import lombok.Getter;

@Getter
public class Money {
    private Long value;
    private Money(Long value) {
        this.value = value;
    }

    public static Money of(Long value) {
        return new Money(value);
    }

    public Money plus(Long value) {
        return new Money(this.value + value);
    }

    public Money multiple(int quantity) {
        return new Money(this.value * quantity);
    }
}

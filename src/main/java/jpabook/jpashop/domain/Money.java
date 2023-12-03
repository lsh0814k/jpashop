package jpabook.jpashop.domain;

import lombok.Getter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(getValue(), money.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

package jpabook.jpashop.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jpabook.jpashop.domain.Money;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, Long> {
    @Override
    public Long convertToDatabaseColumn(Money money) {
        if (money == null) return null;
        else return money.getValue();
    }

    @Override
    public Money convertToEntityAttribute(Long money) {
        if (money == null) return null;
        else return Money.of(money);
    }
}

package jpabook.jpashop.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jpabook.jpashop.domain.Quantity;

@Converter(autoApply = true)
public class QuantityConverter implements AttributeConverter<Quantity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Quantity quantity) {
        if (quantity == null) return null;
        else return quantity.getValue();
    }

    @Override
    public Quantity convertToEntityAttribute(Integer quantity) {
        if (quantity == null) return null;
        else return Quantity.of(quantity);
    }
}

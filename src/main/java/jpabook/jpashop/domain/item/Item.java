package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.CategoryItem;
import jpabook.jpashop.domain.Money;
import jpabook.jpashop.domain.Quantity;
import jpabook.jpashop.domain.common.BaseEntity;
import jpabook.jpashop.domain.converter.MoneyConverter;
import jpabook.jpashop.domain.converter.QuantityConverter;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.InheritanceType.SINGLE_TABLE;
import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@NoArgsConstructor(access = PROTECTED)
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    @Convert(converter = MoneyConverter.class)
    private Money price;

    @Convert(converter = QuantityConverter.class)
    private Quantity stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    public Item(String name, Money price, Quantity stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void addStock(int quantity) {
        this.stockQuantity = stockQuantity.add(quantity);
    }

    public void removeStock(int quantity) {
        this.stockQuantity = stockQuantity.minus(quantity);
    }
}

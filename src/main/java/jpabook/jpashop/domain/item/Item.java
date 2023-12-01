package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.CategoryItem;
import jpabook.jpashop.domain.Money;
import jpabook.jpashop.domain.common.BaseEntity;
import jpabook.jpashop.domain.converter.MoneyConverter;
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
public class Item extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    @Convert(converter = MoneyConverter.class)
    private Money price;

    private Integer stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    public Item(String name, Money price, Integer stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}

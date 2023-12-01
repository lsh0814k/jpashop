package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.common.BaseEntity;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "category_item")
public class CategoryItem extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "category_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}

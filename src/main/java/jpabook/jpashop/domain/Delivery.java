package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@NoArgsConstructor(access = PROTECTED)
public class Delivery {
    public Delivery(DeliveryStatus status, Address address) {
        this.status = status;
        this.address = address;
    }

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Enumerated(STRING)
    private DeliveryStatus status;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;
}

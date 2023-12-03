package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@NoArgsConstructor(access = PROTECTED)
public class Delivery {
    @Builder
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

    public void checkCancelable() {
        if (status.equals(DeliveryStatus.COMP)) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }
    }
}

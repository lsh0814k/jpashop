package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Embeddable @Getter
@NoArgsConstructor(access = PROTECTED)
public class Address {
    @Builder
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    private String city;
    private String street;
    private String zipcode;
}

package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity @Getter
@NoArgsConstructor(access = PROTECTED)

public class Member extends BaseEntity {
    @Builder
    public Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    List<Order> orders = new ArrayList<>();

    public void update(Member member) {
        if (!ObjectUtils.isEmpty(member.getName())) {
            this.name = member.getName();
        }
        if (!ObjectUtils.isEmpty(member.getAddress())) {
            this.address = member.getAddress();
        }
    }
}
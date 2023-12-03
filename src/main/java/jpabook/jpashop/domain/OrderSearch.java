package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderSearch {
    private final String memberName;
    private final OrderStatus orderStatus;
}

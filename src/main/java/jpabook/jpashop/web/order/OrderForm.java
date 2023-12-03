package jpabook.jpashop.web.order;

import lombok.Getter;

@Getter
public class OrderForm {
    private Long memberId;
    private Long itemId;
    private Integer count;
}

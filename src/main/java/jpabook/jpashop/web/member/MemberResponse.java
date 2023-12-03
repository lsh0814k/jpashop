package jpabook.jpashop.web.member;

import jpabook.jpashop.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {

    public MemberResponse(Member member) {
        this.name = member.getName();
        this.city = member.getAddress().getCity();
        this.street = member.getAddress().getStreet();
        this.zipcode = member.getAddress().getZipcode();
    }

    private String name;

    private String city;
    private String street;
    private String zipcode;
}

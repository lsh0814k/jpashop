package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@Slf4j
@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired EntityManager em;

    @Test
    @DisplayName("회원가입")
    void join() {
        //given
        Member member = Member.builder()
                .name("kim")
                .build();
        //when
        Long savedId = memberService.join(member);

        //then
        assertThat(member).isEqualTo(em.find(Member.class, savedId));
    }

    @Test
    @DisplayName("중복회원 예외")
    void joinIllegalStateException() {
        // given
        Member member = Member.builder()
                .name("kim")
                .build();
        memberService.join(member);

        // expected
        Member newMember = Member.builder()
                .name("kim")
                .build();
        assertThatThrownBy(() -> memberService.join(newMember))
                .isInstanceOf(IllegalStateException.class);
    }
}
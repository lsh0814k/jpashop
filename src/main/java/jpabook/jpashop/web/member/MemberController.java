package jpabook.jpashop.web.member;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Validated MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Member member = Member.builder()
                .name(form.getName())
                .address(new Address(form.getCity(), form.getStreet(), form.getZipcode()))
                .build();
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String members(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members.stream().map(MemberResponse::new).collect(Collectors.toList()));

        return "members/memberLisr";
    }
}

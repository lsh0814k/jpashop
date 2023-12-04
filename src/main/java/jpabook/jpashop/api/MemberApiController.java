package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {
    private final MemberService memberService;

    /**
     * 등록 V1: 요청 값으로 Member 엔티티를 직접 받는다.
     * 문제점
     *  - 엔티티에 프레젠테이션 계층을 위한 로직이 추가된다.
     *  - 실무에서는 회원 엔티티를 위한 API가 다양하게 만들어지는데, 한 엔티티에 각각의 API를 위한
     *    모든 요청 요구 사항을 담기는 어렵다.
     *  - 엔티티가 변경되면 API 스펙이 변한다.
     *  결론
     *  - API 요청 스펙에 맞추어 별도의 DTO를 파라미터로 받아야 한다.
     */
    @PostMapping("/v1")
    public CreateMemberResponse saveMemberV1(@RequestBody @Validated Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 등록 V2 : 요청 값으로 Member 엔티티 대신에 별도의 DTO를 받는다.
     * @param request
     * @return
     */
    @PostMapping("/v2")
    public CreateMemberResponse saveMemberV2(@RequestBody @Validated CreateMemberRequest request) {
        Member member = Member.builder()
                .name(request.getName())
                .build();

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @PatchMapping("/v2/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Validated UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findById(id);

        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    static class UpdateMemberResponse {
        private Long id;
        private String name;

        public UpdateMemberResponse(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    /**
     * 조회 V1: 응답 값으로 엔티티를 직접 외부에 노출한다.
     * 문제점
     * - 엔티티에 프레젠테이션 계층을 위한 로직이 추가된다.
     * - 응답 스펙을 맞추기 위해 로직이 추가 된다. (@JsonIgnore, 별도의 뷰 로직 등)
     * - 실무에서는 같은 엔티티에 대해 API가 용도에 따라 다양하게 만들어지는데, 한 엔티티에
     *   각각의 API를 위한 프레젠테이션 응답 로직을 담기는 어렵다.
     *   - 엔티티가 변경되면 API 스펙이 변한다.
     *   - 컬렉션을 직접 반환하면 향후 API 스펙을 변경하기 어렵다(별도의 Result 클래스 생성으로 해결)
     *  결론
     *   - API 응답 스펙에 맞추어 별도의 DTO를 반환한다.
     *
     *  안 좋은 버전, 모든 엔티티가 노출, @JsonIgnore -> 이건 정말 최악, api가 이거 하나 인가,,
     *  화면에 종속적이지 마라!!
     */
    @GetMapping("/v1")
    public List<Member> membersV1() {
        return memberService.findAll();
    }

    @GetMapping("/v2")
    public Result memberV2() {
        List<Member> findMembers = memberService.findAll();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    static class Result<T> {
        private T data;

        public Result(T data) {
            this.data = data;
        }
    }

    @Data
    static class MemberDto {
        private String name;

        public MemberDto(String name) {
            this.name = name;
        }
    }
}

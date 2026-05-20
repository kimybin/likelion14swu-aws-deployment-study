package com.likelion14.PBL_Spring.member.controller;

import com.likelion14.PBL_Spring.member.domain.role.Lion;
import com.likelion14.PBL_Spring.member.domain.role.Role;
import com.likelion14.PBL_Spring.member.domain.role.Staff;
import com.likelion14.PBL_Spring.member.dto.*;
import com.likelion14.PBL_Spring.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@Tag(name="Member", description = "멤버 관리 API")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    private Object toResponse(Role role) {
        if(role instanceof Lion lion) {
            return LionResponse.from(lion);
        } else if(role instanceof Staff staff) {
            return StaffResponse.from(staff);
        }
        return role;
    }

    // POST /members/lions - Lion 등록
    @Operation(summary = "Lion(아기사자) 등록")
    @PostMapping("/lions")
    public ResponseEntity<LionResponse> createLion(@RequestBody LionCreateRequest request) {
        Role lion = memberService.createLion(request);
        if(lion == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(LionResponse.from((Lion) lion));

    }

    // POST /members/staffs - Staffs 등록
    @Operation(summary = "Staff(운영진) 등록")
    @PostMapping("/staffs")
    public ResponseEntity<StaffResponse> createStaff(@RequestBody StaffCreateRequest request) {
        Role staff = memberService.createStaff(request);
        if(staff == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(StaffResponse.from((Staff) staff));
    }

    // SearchByName () 메서드를 사용하여 Controller에서 검색결과를 반환
    // GET /members/{name} - 단건 조회
    @Operation(summary = "이름으로 단일 멤버 조회")
    @GetMapping("/{name}")
    public ResponseEntity<?> getMember(@PathVariable String name) {
        Role member = memberService.searchByName(name);

        if(member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponse(member));
    }

    // MemberController에 필드를 수정하는 로직
    // PUT /members/lions/{name} - Lion 수정
    @Operation(summary = "Lion 정보 수정")
    @PutMapping("/lions/{name}")
    public ResponseEntity<LionResponse> updateLion(@PathVariable String name,
                                                   @RequestBody LionUpdateRequest request) {
        Role updated = memberService.updateLion(name, request);
        if(updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(LionResponse.from((Lion) updated));
    }

    @Operation(summary = "Staff 정보 수정")
    @PutMapping("/staffs/{name}")
    public ResponseEntity<StaffResponse> updateStaff(@PathVariable String name,
                                                   @RequestBody StaffUpdateRequest request) {
        Role updated = memberService.updateStaff(name, request);
        if(updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(StaffResponse.from((Staff) updated));
    }

    @Operation(summary = "멤버 삭제")
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteMember(@PathVariable String name) {
        boolean sucess = memberService.deleteMember(name);

        if(!sucess) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // 전체 멤버 조회 + 이름으로 검색
    @Operation(summary = "전체 회원 조회 또는 이름으로 검색 ")
    @GetMapping
    // @PathVariable → /members/{name} : URL 경로의 일부가 변수
    // @RequestParam → /members?name=X : 쿼리 파라미터
    public ResponseEntity<?> getMembers(
            @RequestParam(required = false) String name) {
            if(name!=null) {
                Role member = memberService.searchByName(name);
                if (member == null) return ResponseEntity.notFound().build();
                return ResponseEntity.ok(toResponse(member));
            }
        return ResponseEntity.ok(memberService.getAllMembers());
    }
}
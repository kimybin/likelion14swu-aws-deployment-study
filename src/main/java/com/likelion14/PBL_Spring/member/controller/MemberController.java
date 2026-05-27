package com.likelion14.PBL_Spring.member.controller;

import com.likelion14.PBL_Spring.member.domain.Member;
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

    // POST /members/lions - Lion 등록
    @Operation(summary = "Lion(아기사자) 등록")
    @PostMapping("/lions")
    public ResponseEntity<MemberResponse> createLion(@RequestBody LionCreateRequest request) {
        Member member = memberService.createLion(request);
        if(member == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(MemberResponse.from(member));

    }

    // POST /members/staffs - Staffs 등록
    @Operation(summary = "Staff(운영진) 등록")
    @PostMapping("/staffs")
    public ResponseEntity<MemberResponse> createStaff(@RequestBody StaffCreateRequest request) {
        Member member = memberService.createStaff(request);
        if(member == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(MemberResponse.from(member));
    }

    // PUT /members/lions/{id} - Lion 수정
    @Operation(summary = "Lion 정보 수정")
    @PutMapping("/lions/{id}")
    public ResponseEntity<MemberResponse> updateLion(@PathVariable Long id,
                                                   @RequestBody LionUpdateRequest request) {
        Member updated = memberService.updateLion(id, request);
        if(updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MemberResponse.from(updated));
    }

    // PUT /members/staffs/{id} - Staff 수정
    @Operation(summary = "Staff 정보 수정")
    @PutMapping("/staffs/{id}")
    public ResponseEntity<MemberResponse> updateStaff(@PathVariable Long id,
                                                   @RequestBody StaffUpdateRequest request) {
        Member updated = memberService.updateStaff(id, request);
        if(updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MemberResponse.from(updated));
    }

    // DELEE /members/{id} - 멤버 삭제
    @Operation(summary = "멤버 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        boolean sucess = memberService.deleteMember(id);

        if(!sucess) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // 전체 멤버 조회 / id로 멤버 조회
    // GET /members - 전체 조회
    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMemebers() {
        List<MemberResponse> responses = memberService.getAllMembers().stream()
                .map(MemberResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    // GET /members/{id} - 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long id) {
        Member member = memberService.findById(id);
        if(member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MemberResponse.from(member));
    }
}
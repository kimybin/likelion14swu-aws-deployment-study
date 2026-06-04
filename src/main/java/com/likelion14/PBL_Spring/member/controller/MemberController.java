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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MemberResponse.from(memberService.createLion(request)));
    }

    // POST /members/staffs - Staffs 등록
    @Operation(summary = "Staff(운영진) 등록")
    @PostMapping("/staffs")
    public ResponseEntity<MemberResponse> createStaff(@RequestBody StaffCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MemberResponse.from(memberService.createStaff(request)));
    }

    // PUT /members/lions/{id} - Lion 수정
    @Operation(summary = "Lion 정보 수정")
    @PutMapping("/lions/{id}")
    public ResponseEntity<MemberResponse> updateLion(@PathVariable Long id,
                                                   @RequestBody LionUpdateRequest request) {

        return ResponseEntity.ok(MemberResponse.from(memberService.updateLion(id, request)));
    }

    // PUT /members/staffs/{id} - Staff 수정
    @Operation(summary = "Staff 정보 수정")
    @PutMapping("/staffs/{id}")
    public ResponseEntity<MemberResponse> updateStaff(@PathVariable Long id,
                                                   @RequestBody StaffUpdateRequest request) {

        return ResponseEntity.ok(MemberResponse.from(memberService.updateStaff(id, request)));
    }

    // DELEE /members/{id} - 멤버 삭제
    @Operation(summary = "멤버 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "전체 멤버 검색")
    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers(@RequestParam(required = false) String part) {
        List<MemberResponse> responses;

        if(part != null) {
            responses = memberService.getMembersByPart(part).stream()
                    .map(MemberResponse::from)
                    .toList();
        } else {
            responses = memberService.getAllMembers().stream()
                    .map(MemberResponse::from)
                    .toList();
        }

        return ResponseEntity.ok(responses);
    }

    // GET /members/{id} - member id로 단건 조회
    @Operation(summary = "id로 멤버 검색")
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(MemberResponse.from(memberService.findById(id)));
    }

    // GET /members/search - member 이름으로 단건 조회
    @Operation(summary = "이름으로 멤버 검색")
    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(MemberResponse.from(memberService.searchByName(name)));
    }


}
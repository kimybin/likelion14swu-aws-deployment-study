package com.likelion14.PBL_Spring.member.controller;

import com.likelion14.PBL_Spring.member.domain.Assignment;
import com.likelion14.PBL_Spring.member.dto.AssignmentCreateRequest;
import com.likelion14.PBL_Spring.member.dto.AssignmentResponse;
import com.likelion14.PBL_Spring.member.dto.AssignmentUpdateRequest;
import com.likelion14.PBL_Spring.member.repository.AssignmentRepository;
import com.likelion14.PBL_Spring.member.service.AssignmentService;
import com.likelion14.PBL_Spring.member.service.ViewCountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AssignmentController {
    private final AssignmentService assignmentService;
    private final ViewCountService viewCountService;

    public AssignmentController(AssignmentService assignmentService, ViewCountService viewCountService) {
        this.assignmentService = assignmentService;
        this.viewCountService = viewCountService;
    }

    // POST /members/{memberId}/assignments - 과제 등록
    @PostMapping("/members/{memberId}/assignments")
    public ResponseEntity<AssignmentResponse> create(@PathVariable Long memberId,
                                                     @RequestBody AssignmentCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AssignmentResponse.from(assignmentService.create(memberId, request)));
    }

    // GET /assignments/{id} - 과제 단건 조회
    @GetMapping("/assignments/{id}")
    public ResponseEntity<AssignmentResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok(AssignmentResponse.from(assignmentService.findById(id)));
    }

    // GET /members/{memberId}/assignments - 멤버별 과제 목록 조회
    @GetMapping("/members/{memberId}/assignments")
    public ResponseEntity<List<AssignmentResponse>> findByMemberId(@PathVariable Long memberId) {
        List<AssignmentResponse> responses = assignmentService.findByMemberId(memberId).stream()
                .map(AssignmentResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    // PUT /assignments/{id} - 과제 수정
    @PutMapping("/assignments/{id}")
    public ResponseEntity<AssignmentResponse> update(@PathVariable Long id,
                                                     @RequestBody AssignmentUpdateRequest request) {

        return ResponseEntity.ok(AssignmentResponse.from(assignmentService.update(id, request)));
    }

    // DELETE /assignments/{id} - 과제 삭제
    @DeleteMapping("/assignments/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        assignmentService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // 과제 제목 검색
    @GetMapping("/assignments/search")
    public ResponseEntity<List<AssignmentResponse>> searchByTitle(@RequestParam String keyword) {
        List<AssignmentResponse> responses = assignmentService.searchByTitle(keyword).stream()
                .map(AssignmentResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    // 과제 제목 검색
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentResponse>> getAllAssignments() {
        List<AssignmentResponse> responses = assignmentService.findAll().stream()
                .map(AssignmentResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "id로 과제 조회")
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponse> getAssignment(@PathVariable Long id) {
        Assignment assignment = assignmentService.findById(id);   // 먼저 존재 확인
        Long viewCount = viewCountService.increase(id);           // 그다음 조회수 증가
        return ResponseEntity.ok(AssignmentResponse.from(assignment, viewCount));
    }


}

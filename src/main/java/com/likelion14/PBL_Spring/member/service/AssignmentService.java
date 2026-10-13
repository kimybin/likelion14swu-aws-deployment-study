package com.likelion14.PBL_Spring.member.service;

import com.likelion14.PBL_Spring.global.exception.AssignmentNotFoundException;
import com.likelion14.PBL_Spring.global.exception.MemberNotFoundException;
import com.likelion14.PBL_Spring.member.domain.Assignment;
import com.likelion14.PBL_Spring.member.domain.Member;
import com.likelion14.PBL_Spring.member.dto.AssignmentCreateRequest;
import com.likelion14.PBL_Spring.member.dto.AssignmentUpdateRequest;
import com.likelion14.PBL_Spring.member.repository.AssignmentRepository;

import com.likelion14.PBL_Spring.member.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final MemberRepository memberRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, MemberRepository memberRepository) {
        this.assignmentRepository = assignmentRepository;
        this.memberRepository = memberRepository;
    }

    // 과제 등록
    @Transactional
    public Assignment create(Long memberId, AssignmentCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당 멤버를 찾을 수 없습니다. id: " + memberId));

        Assignment assignment = new Assignment(
                request.getTitle(), request.getDescription(), member
        );

        return assignmentRepository.save(assignment);
    }

    // 멤버별 조회
    public List<Assignment> findByMemberId(Long memberId) {
        return assignmentRepository.findByMemberId(memberId);
    }

    // 단건 조회
    public Assignment findById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new AssignmentNotFoundException("해당 과제를 찾을 수 없습니다. id: " + id));
    }

    // 과제 수정
    @Transactional
    public Assignment update(Long id, AssignmentUpdateRequest request) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new AssignmentNotFoundException("해당 과제를 찾을 수 없습니다. id: " + id));

        assignment.updateInfo(request.getTitle(), request.getDescription());
        return assignmentRepository.save(assignment);
    }

    // 과제 삭제
    @Transactional
    public void delete(Long id) {
        if(!assignmentRepository.existsById(id)) {
            throw new AssignmentNotFoundException("해당 과제를 찾을 수 없습니다. id: " + id);
        }
        assignmentRepository.deleteById(id);
    }

    // 과제 제목 검색
    public List<Assignment> searchByTitle(String keyword) {
        return assignmentRepository.findByTitleContaining(keyword);
    }

    // 전체 조회
    public List<Assignment> findAll() {
        return assignmentRepository.findAll();
    }

}

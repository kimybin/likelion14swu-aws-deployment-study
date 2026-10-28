package com.likelion14.PBL_Spring.member.dto;

import com.likelion14.PBL_Spring.member.domain.Assignment;
import lombok.Getter;

@Getter
public class AssignmentResponse {

    // 필드 정의
    // 타입은 Assignment 엔티티에 정의된 타입이랑 동일하게
    private final Long id;
    private final String title;
    private final String description;
    private final Long memberId;
    private final String memberName;
    private final Long viewCount;

    // 내부 생성자
    private AssignmentResponse(Long id, String title, String description, Long memberId, String memberName, Long viewCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.memberId = memberId;
        this.memberName = memberName;
        this.viewCount = viewCount;

    }

    // 기존 from() — 목록 조회 등 조회수가 필요 없는 곳에서 사용
    public static AssignmentResponse from(Assignment assignment) {
        return from(assignment, null);
    }

    // 조회수를 함께 담는 from()
    public static AssignmentResponse from(Assignment assignment, Long viewCount) {
        return new AssignmentResponse(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getMember() != null ? assignment.getMember().getId() : null,
                assignment.getMember() != null ? assignment.getMember().getName() : null,
                viewCount
        );
    }
}

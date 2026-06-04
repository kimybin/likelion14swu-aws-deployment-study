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

    // 내부 생성자
    private AssignmentResponse(Long id, String title, String description, Long memberId, String memberName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.memberId = memberId;
        this.memberName = memberName;
    }

    // 엔티티를 DTO로 변환
    // 정적 팩토리 메소드 from(): Assignment 엔티티에서 필요한 값을 꺼내와 내부 생성자를 호출하여 DTO에 전달
    // DB에 저장된 원본 객체의 정보를 손상없이 클라이언트에게 전달
    public static AssignmentResponse from(Assignment assignment) {
        return new AssignmentResponse(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDescription(),

                // memberId와 memberName은 Assignment가 참조하는 Member에서 가져옴
                assignment.getMember() != null ? assignment.getMember().getId() : null,
                assignment.getMember() != null ? assignment.getMember().getName() : null
        );
    }
}

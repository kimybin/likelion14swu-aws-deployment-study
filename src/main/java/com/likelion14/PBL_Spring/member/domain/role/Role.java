package com.likelion14.PBL_Spring.member.domain.role;

import com.likelion14.PBL_Spring.member.domain.policy.SubmissionPolicy;

// 공통 속성 관리 클래스
public abstract class Role {
    // 공통 속성
    private String name; // 이름
    private String major; // 전공
    private int generation; // 기수
    private String part; // 파트

    // 생성자
    protected Role(String name, String major, int generation, String part) {
        this.name = name;
        this.major = major;
        this.generation = generation;
        this.part = part;
    }

    // getter 메서드
    public final String getName() { return name; }

    public final String getMajor() {
        return major;
    }

    public final int getGeneration() {
        return generation;
    }

    public final String getPart() {
        return part;
    }

    // 과제 제출 정책 반환 메서드 (인터페이스 말고 추상 메서드로 선언)
    public abstract SubmissionPolicy submissionPolicy();

    // 역할별 상세 정보
    public abstract String getInfo();

    // 역할명
    public abstract String roleName();

    // 과제 제출 가능 여부 반환
    public boolean canSubmitAssignment() {
        return submissionPolicy().canSubmit();
    };
}

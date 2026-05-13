package com.likelion14.PBL_Spring.member.domain.policy;

public class LionSubmissionPolicy implements SubmissionPolicy {

    private int minGeneration;
    private int lionGeneration;

    public LionSubmissionPolicy(int minGeneration, int lionGeneration) {
        this.minGeneration = minGeneration;
        this.lionGeneration = lionGeneration;
    }
    // 아기사자 과제 제출 정책 인터페이스 - 과제 제출 가능 여부 true
    @Override
    public boolean canSubmit() {
        return minGeneration <= lionGeneration;
    }
}

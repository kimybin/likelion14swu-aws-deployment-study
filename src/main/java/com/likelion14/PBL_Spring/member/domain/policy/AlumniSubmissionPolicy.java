package com.likelion14.PBL_Spring.member.domain.policy;

public class AlumniSubmissionPolicy implements SubmissionPolicy {

    // 알럼나이 과제 제출 정책 인터페이스 - 과제 제출 가능 여부 false
    @Override
    public boolean canSubmit() {
        return false;
    }
}

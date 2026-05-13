package com.likelion14.PBL_Spring.member.domain.role;

import com.likelion14.PBL_Spring.member.domain.policy.StaffSubmissionPolicy;
import com.likelion14.PBL_Spring.member.domain.policy.SubmissionPolicy;

// 운영자 클래스
public class Staff extends Role {
    private String position; // 직책

    public Staff (String name, String major, int generation, String part, String position) {
        super(name, major, generation, part);
        this.position = position;
    }

    @Override
    public SubmissionPolicy submissionPolicy() {
        return new StaffSubmissionPolicy();
    }

    @Override
    public String getInfo() {
        return "👤이름: " + getName() + " | 🎓전공: " + getMajor() +
                " | 📌기수: " + getGeneration() + " | 💻파트: " + getPart() +
                "\n🌟직책: " + position;
    }

    public String roleName() {
        return "운영진";
    }

}
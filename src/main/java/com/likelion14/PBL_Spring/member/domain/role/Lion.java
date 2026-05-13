package com.likelion14.PBL_Spring.member.domain.role;

import com.likelion14.PBL_Spring.member.domain.policy.LionSubmissionPolicy;
import com.likelion14.PBL_Spring.member.domain.policy.SubmissionPolicy;

// 아기사자 클래스
public class Lion extends Role {
    //private String number; // 학번
    private int minGeneration;
    private String studendId;

    // 생성자
    public Lion(String name, String major, int generation, String part, String number, int minGeneration) {
        super(name, major, generation, part);
        //this.number = number;
        this.minGeneration = minGeneration;
    }

    public Lion(String name, String major, int generation, String part, String studentId) {
        super(name, major, generation, part);
        this.studendId = studentId;
    }

    @Override
    public SubmissionPolicy submissionPolicy() {
        return new LionSubmissionPolicy(minGeneration, getGeneration());
    }

    @Override
    public String getInfo() {
        return "👤이름: " + getName() + " | 🎓전공: " + getMajor() +
                " | 📌기수: " + getGeneration() + " | 💻파트: " + getPart() +
                "\n🆔학번: " + studendId;
    }

    public String roleName() {
        return "아기사자";
    }
}

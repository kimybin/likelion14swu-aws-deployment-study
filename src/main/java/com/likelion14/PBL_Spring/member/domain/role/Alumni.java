package com.likelion14.PBL_Spring.member.domain.role;


import com.likelion14.PBL_Spring.member.domain.policy.AlumniSubmissionPolicy;
import com.likelion14.PBL_Spring.member.domain.policy.SubmissionPolicy;

public class Alumni extends Role {
    private String presentPart; // 현재 직무

    // 생성자
    public Alumni (String name, String major, int generation, String part, String presentPart) {
        super(name, major, generation, part);
        this.presentPart = presentPart;
    }

    @Override
    public SubmissionPolicy submissionPolicy() {
        return new AlumniSubmissionPolicy();
    }

    @Override
    public String getInfo() {
        return "👤이름: " + getName() + " | 🎓전공: " + getMajor() +
                " | 📌기수: " + getGeneration() + " | 💻파트: " + getPart() +
                "\n💼현재 직무: " + presentPart;

    }

    public String roleName() {
        return "알럼나이";
    }


}

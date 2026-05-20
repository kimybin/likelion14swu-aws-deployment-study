package com.likelion14.PBL_Spring.member.dto;

import com.likelion14.PBL_Spring.member.domain.role.Lion;

public class LionResponse {
    private String name;
    private String major;
    private int generation;
    private String part;
    private String roleName;
    private String studentId;

    public String getName() { return name; }
    public String getMajor() { return major; }
    public int getGeneration() { return generation; }
    public String getPart() { return part; }
    public String getRoleName() { return roleName; }
    public String getStudentId() { return studentId; }

    public static LionResponse from(Lion lion) {
        LionResponse response = new LionResponse();

        response.name = lion.getName();
        response.major = lion.getMajor();
        response.generation = lion.getGeneration();
        response.part = lion.getPart();
        response.roleName = lion.roleName();
        response.studentId = lion.getStudentId();
        return response;
    }
}

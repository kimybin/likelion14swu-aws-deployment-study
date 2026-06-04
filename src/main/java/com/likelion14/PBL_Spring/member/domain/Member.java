package com.likelion14.PBL_Spring.member.domain;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String major;
    private String part;
    private int generation;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private String studentId;
    private String position;

    protected Member() {
    }

    @Builder
    public Member(String name, String major, int generation, String part,
                  RoleType roleType, String studentId, String position) {
        this.name = name;
        this.major = major;
        this.generation = generation;
        this.part = part;
        this.roleType = roleType;
        this.studentId = studentId;
        this.position = position;
    }

    // 수정용 메서드
    public void updateInfo(String major, int generation, String part) {
        this.major = major;
        this.generation = generation;
        this.part = part;
    }

    public void updateStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void updatePosition(String position) {
        this.position = position;
    }

    // Getter
//    public Long getId() { return id; }
//
//    public String getName() { return name; }
//    public String getMajor() { return major; }
//    public int getGeneration() { return generation; }
//    public String getPart() { return part; }
//    public RoleType getRoleType() { return roleType; }
//    public String getStudentId() { return studentId; }
//    public String getPosition() { return position; }


    // Member 엔티티에 양방향 관계 추가
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true) // 멤버 삭제 시 해당 멤버 과제도 같이 삭제되도록
    private List<Assignment> assignmentList = new ArrayList<>();
}

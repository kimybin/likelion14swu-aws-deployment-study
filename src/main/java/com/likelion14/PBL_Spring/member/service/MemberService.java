package com.likelion14.PBL_Spring.member.service;


import com.likelion14.PBL_Spring.member.domain.role.Lion;
import com.likelion14.PBL_Spring.member.domain.role.Role;
import com.likelion14.PBL_Spring.member.domain.role.Staff;
import com.likelion14.PBL_Spring.member.dto.*;
import com.likelion14.PBL_Spring.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    // private MemoryMemberRepository repository = new MemoryMemberRepository();

    // 인터페이스에 의존 (구현체에 의존하지 않음)
    private final MemberRepository repository;

    // 생성자를 통해 의존성 주입
    @Autowired
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public boolean register(Role member) {
        if(repository.existsByName(member.getName())) {
            return false;
        }
        repository.save(member);
        return true;
    }

    public Role searchByName(String name) { return repository. findByName(name); }

    public List<?> getAllMembers() {
        return repository.findAll().stream()
                .map(role -> {
                    if (role instanceof Lion lion) return (Object) LionResponse.from(lion);
                    if (role instanceof Staff staff) return (Object) StaffResponse.from(staff);
                    return (Object) role;
                })
                .collect(Collectors.toList());
    }

    public boolean isEmpty() { return repository.findAll().isEmpty(); }

    public Role createLion(LionCreateRequest request) {
        Lion lion = new Lion(request.getName(), request.getMajor(), request.getGeneration(), request.getPart(), request.getStudentId());

        // name 값이 이미 존재한다면
        if(repository.existsByName(lion.getName())) {
            return null;
        }
        repository.save(lion);
        return lion;
    }

    public Role createStaff(StaffCreateRequest request) {
        Staff staff = new Staff(request.getName(), request.getMajor(), request.getGeneration(), request.getPart(), request.getPosition());

        if(repository.existsByName(staff.getName())) {
            return null;
        }
        repository.save(staff);
        return staff;
    }

    // Lion 수정
    public Role updateLion(String name, LionUpdateRequest request) {
        // name을 찾지 못했다면
        if(!repository.existsByName(name)){
            return null;
        }

        Lion updated = new Lion(name, request.getMajor(), request.getGeneration(), request.getPart(), request.getStudentId());
        repository.updateByName(name, updated);
        return updated;
    }

    // Staff 수정
    public Role updateStaff(String name, StaffUpdateRequest request) {
        if(!repository.existsByName(name)) {
            return null;
        }

        Staff updated = new Staff(name, request.getMajor(), request.getGeneration(), request.getPart(), request.getPosition());
        repository.updateByName(name, updated);
        return updated;
    }

    // 객체 삭제
    public boolean deleteMember(String name) {
        return repository.deleteMember(name);
    }



}
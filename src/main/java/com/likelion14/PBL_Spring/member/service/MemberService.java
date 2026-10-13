package com.likelion14.PBL_Spring.member.service;


import com.likelion14.PBL_Spring.global.exception.DuplicateMemberException;
import com.likelion14.PBL_Spring.global.exception.MemberNotFoundException;
import com.likelion14.PBL_Spring.member.domain.Member;
import com.likelion14.PBL_Spring.member.domain.RoleType;

import com.likelion14.PBL_Spring.member.dto.*;
import com.likelion14.PBL_Spring.member.repository.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MemberService {
    // 인터페이스에 의존 (구현체에 의존하지 않음)
    private final MemberRepository repository;
    private final S3Service s3Service;

    // 생성자를 통해 의존성 주입
    @Autowired
    public MemberService(MemberRepository repository, S3Service s3Service) {
        this.repository = repository;
        this.s3Service = s3Service;
    }

    // 이름으로 Member 조회
    public Member searchByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new MemberNotFoundException("해당 멤버를 찾을 수 없습니다. name: " + name));
    }

    // 전체 조회
    public List<Member> getAllMembers() {
        return repository.findAll();
    }

    // ID로 Member 조회
    public Member findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("해당 멤버를 찾을 수 없습니다. id: " + id));
    }

    // Lion 등록
    @Transactional
    public Member createLion(LionCreateRequest request) {
        if(repository.existsByName(request.getName())) {
            throw new MemberNotFoundException("이미 존재하는 이름입니다. name: " + request.getName());
        }

        Member lion = new Member(request.getName(), request.getMajor(), request.getGeneration(),
                request.getPart(), RoleType.LION, request.getStudentId(), null);

        return repository.save(lion);
    }

    // Staff 등록
    @Transactional
    public Member createStaff(StaffCreateRequest request) {
        if(repository.existsByName(request.getName())) {
            throw new MemberNotFoundException("이미 존재하는 이름입니다. name: " + request.getName());
        }

        Member staff = new Member(request.getName(), request.getMajor(), request.getGeneration(),
                request.getPart(), RoleType.STAFF, null, request.getPosition());

        return repository.save(staff);
    }

    // Lion 수정
    @Transactional
    public Member updateLion(Long id, LionUpdateRequest request) {
        Member lion = repository.findById(id)
                        .orElseThrow(()-> new MemberNotFoundException("해당 멤버를 찾을 수 없습니다. id: " + id));

        lion.updateInfo(request.getMajor(), request.getGeneration(), request.getPart());
        lion.updateStudentId(request.getStudentId());
        return repository.save(lion);
    }

    // Staff 수정
    @Transactional
    public Member updateStaff(Long id, StaffUpdateRequest request) {
        Member staff = repository.findById(id)
                .orElseThrow(() ->new MemberNotFoundException("해당 멤버를 찾을 수 없습니다. id: " + id));

        staff.updateInfo(request.getMajor(), request.getGeneration(), request.getPart());
        staff.updatePosition(request.getPosition());
        return repository.save(staff);
    }

    // Member 삭제
    @Transactional
    public void deleteMember(Long id) {
        if(!repository.existsById(id)) {
            throw new MemberNotFoundException("해당 멤버를 찾을 수 없습니다. id: " + id);
        }
        repository.deleteById(id);
    }

    // 파트별 Member 목록 조회
    public List<Member> getMembersByPart(String part) {
        return repository.findByPart(part);
    }

    @Transactional
    public Member updateProfileImage(Long id, MultipartFile file) {
        Member member = repository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("해당 멤버를 찾을 수 없습니다. id: " + id));

        String url = s3Service.uploadProfileImage(file);
        member.updateProfileImageUrl(url);
        return repository.save(member);
    }

}
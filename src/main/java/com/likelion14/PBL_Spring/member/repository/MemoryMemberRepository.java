package com.likelion14.PBL_Spring.member.repository;
import com.likelion14.PBL_Spring.member.domain.role.Role;
import com.likelion14.PBL_Spring.member.dto.LionCreateRequest;
import com.likelion14.PBL_Spring.member.dto.LionUpdateRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryMemberRepository implements MemberRepository {
    private List<Role> members = new ArrayList<>();

    @Override
    public void save(Role member) {
        members.add(member);
    }

    @Override
    public Role findByName(String name) {
        for(Role member: members) {
            if(member.getName().equals(name)) {
                return member;
            }
        }
        return null;
    }

    @Override
    public List<Role> findAll() {
        return members;
    }

    @Override
    public boolean existsByName(String name) {
        for( Role member : members) {
            if(member.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // 객체를  이름으로 검색하여 , 다른 필드를 수정
    @Override
    public void updateByName(String name, Role role) {
        for(int i = 0; i < members.size(); i++) {
            if(members.get(i).getName().equals(name)) {
                members.set(i, role);
                return;
            }
        }
    }

    @Override
    public boolean deleteMember(String name) {
        for(Role member : members) {
            if(member.getName().equals(name)) {
                members.remove(member);
                return true;
            }
        }
        return false;
    }
}

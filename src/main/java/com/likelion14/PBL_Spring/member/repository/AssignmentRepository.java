package com.likelion14.PBL_Spring.member.repository;

import com.likelion14.PBL_Spring.member.domain.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByMemberId(Long memberId);

    @Query("select a from Assignment a where a.title like %:keyword%")
    List<Assignment> searchByTitle(@Param("keyword") String keyword);
}
package com.likelion14.PBL_Spring.member.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.likelion14.PBL_Spring.member.domain.Member;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

// JpaRepository로 전환
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    boolean existsByName(String name);

    @Query("select m from Member m where m.name=:name")
    Optional<Member> findByNameUsingJpql(String name);
}
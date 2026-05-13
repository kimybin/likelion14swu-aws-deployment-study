package com.likelion14.PBL_Spring.member.config;

import com.likelion14.PBL_Spring.member.repository.MemberRepository;
import com.likelion14.PBL_Spring.member.repository.MemoryMemberRepository;
import com.likelion14.PBL_Spring.member.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration <- 과제 2: 수동 주입
public class AppConfig {

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
}
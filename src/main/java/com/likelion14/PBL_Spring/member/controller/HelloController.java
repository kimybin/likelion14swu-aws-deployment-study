package com.likelion14.PBL_Spring.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HelloController {
    @GetMapping("/hello")
    @ResponseBody // 과제 3 : @Controller vs @RestController
    public String hello() {
        return "Hello, Likelion!";
    }

//    과제 1 : 이름을 받아 인사하는 API 구현하기
//    @GetMapping("/hello/{name}")
//    public String hello(@PathVariable String name) {
//        return "Hello, " + name;
//
//    }
}

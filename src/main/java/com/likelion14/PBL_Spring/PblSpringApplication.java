package com.likelion14.PBL_Spring;

import com.likelion14.PBL_Spring.member.service.MemberService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PblSpringApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(PblSpringApplication.class, args);

		// Bean 가져오기 테스트
		MemberService service = context.getBean(MemberService.class);
		System.out.println("서비스 가져오기 성공: " + service);

		// 과제 2: 등록된 Bean 목록 출력하기
		printBeanList(context);

	}

	// 과제 2: 등록된 Bean 목록 출력하기
	public static void printBeanList(ApplicationContext context) {
		System.out.println("==== 등록된 Bean 목록 ====");

		String[] beans = context.getBeanDefinitionNames();
		for(String bean : beans) {
			System.out.println("Bean: " + bean);
		}
	}

}

package com.ljz.compilationVSM.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.ljz.compilationVSM.infrastructure.mapper")
@ComponentScan(basePackages = {"com.ljz.compilationVSM"})
public class CompilationVSMApplication {
	public static void main(String[] args) {
		SpringApplication.run(CompilationVSMApplication.class, args);
	}

}

package com.ljz.compilationVSM;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ljz.compilationVSM.dao")
public class CompilationVSMApplication {
	public static void main(String[] args) {
		SpringApplication.run(CompilationVSMApplication.class, args);
	}

}

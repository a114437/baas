package com.web.frame;

//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;

@SpringBootApplication
@RestController
@EnableTransactionManagement
@EnableScheduling
@EnableWebMvc
@ServletComponentScan(basePackages={"com.web.frame.web"})
@ComponentScan(basePackages={"com.web.frame"})
//@MapperScan(basePackages="com.web.frame")
@EnableSwaggerBootstrapUI
public class Application {
	
	public static void main(String[] args) {
		 
		SpringApplication.run(Application.class, args);
	}
	
}

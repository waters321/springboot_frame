package com.sinocontact.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;




/**
 * Spring Boot 的启动app（可以直接在Tomcat等j2ee容器中运行）
 * @author sam
 * @since 2018年5月15日
 */
@Configuration  
@ComponentScan  
@EnableAutoConfiguration
@SpringBootApplication
@EnableTransactionManagement
public class App  extends SpringBootServletInitializer{	
	@Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(App.class);
    }
	
	public static void main(String[] args) {		
		SpringApplication.run(App.class, args);
	}

}

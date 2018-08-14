package com.sinocontact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * springboot 启动入口，本入口支持servlet容器，也支持自身直接运行
 * @author sam
 * @since 2018年5月17日
 */
@Configuration  
@ComponentScan  
@EnableAutoConfiguration
@SpringBootApplication
public class App extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}

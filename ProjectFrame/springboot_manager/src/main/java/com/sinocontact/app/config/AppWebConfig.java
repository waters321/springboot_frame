package com.sinocontact.app.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sinocontact.app.interceptor.SecurityInterceptor;

/**
 * app的配置类,对登录进行拦截，资源目录进行指定
 * @author sam
 * @since 2018年5月14日
 */
@Configuration
public class AppWebConfig extends WebMvcConfigurerAdapter {

	
    /**
     * 添加资源路径
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
    	//添加静态资源路径
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        
        //添加其他资源路径
        //...
    }
    
    /**
     * 添加拦截器
     */
    public void addInterceptors(InterceptorRegistry registry) {
    	
    	//添加一个登录拦截器
    	addSecurityInterceptor(registry);
        
        
        //添加其他拦截器
        //........
    }

    /**
     * 添加登录拦截器
     * @param registry void 
     * @author sam
     * @since 2018年5月15日
     */
    private void addSecurityInterceptor(InterceptorRegistry registry){
    	
    	//添加一个登录拦截器
    	InterceptorRegistration addInterceptor = registry.addInterceptor(new SecurityInterceptor());
        // 排除配置
        //...addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/user/login**");
        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }
    
   
}

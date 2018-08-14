package com.sinocontact.config;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sinocontact.interceptor.AccessLogInterceptor;
import com.sinocontact.interceptor.WxAuthInterceptor;

/**
 * @author jack
 * @since 2018/2/9
 */
@Configuration
public class AppWebConfig extends WebMvcConfigurerAdapter {
    
	 /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    	//添加一个微信授权拦截器(如果不需要微信授权，只要注释掉addWxAuthInterceptor(registry)即可)
    	addWxAuthInterceptor(registry);
        
        //添加一个访问日志拦截器
    	addAccessLogInterceptor(registry);
    	
    	
        //添加其他拦截器
        //........
    	
        super.addInterceptors(registry);
    }

    /**
     * 添加资源路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    	//添加静态资源路径
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");                 
        
        //添加其他资源路径
        //...

        super.addResourceHandlers(registry);
    }
    
    /**
     * 添加微信授权拦截器
     * @param registry void 
     * @author sam
     * @since 2018年5月15日
     */
    private void addWxAuthInterceptor(InterceptorRegistry registry){    	
    	
    	//添加一个登录拦截器
    	InterceptorRegistration addInterceptor = registry.addInterceptor(new WxAuthInterceptor());
    	
        // 排除配置
        //...addInterceptor.excludePathPatterns("/error");
        
        // 拦截配置(拦截/activity/下的所有)
        addInterceptor.addPathPatterns("/activity/**");        
    }
    /**
     * 添加访问日志拦截器
     * @param registry void 
     * @author sam
     * @since 2018年5月17日
     */
    private void addAccessLogInterceptor(InterceptorRegistry registry){
    	
    	//添加一个访问日志拦截器
    	InterceptorRegistration addInterceptor = registry.addInterceptor(new AccessLogInterceptor());
        
        // 拦截配置(拦截所有)
        addInterceptor.addPathPatterns("/**");
       
    }
}

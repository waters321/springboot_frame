package com.sinocontact.interceptor;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.sinocontact.core.BaseAction;
import com.sinocontact.core.service.AccessLogService;

/**
 * 访问日志拦截器
 * @author sam
 * @since 2018年5月22日
 */
public class AccessLogInterceptor extends MethodFilterInterceptor {
	private static final Logger logger = Logger.getLogger(AccessLogInterceptor.class);
	
	

	@Override
	public String doIntercept(ActionInvocation invocation) throws Exception {
		
		//如果有访问日志需要保存到数据库，可以在此添加写数据库代码		
		BaseAction baseAction = (BaseAction)invocation.getAction();		
		//在此添加访问日志写入到数据库中
		AccessLogService.addAccessLog(baseAction.getRequest(), baseAction.getResponse());
		
		return  invocation.invoke();
	}

}

package com.sinocontact.core.action;



import org.apache.log4j.Logger;


import com.sinocontact.core.BaseAction;


/**
 * 默认的活动首页action
 * @author sam
 * @since 2018年5月21日
 */
public class ActivityAction extends BaseAction{

	private static final Logger logger = Logger.getLogger(ActivityAction.class);
	
	/**
	 * 登录成功后的首页
	 * @return String 
	 * @author sam
	 * @since 2018年5月22日
	 */
	public String index(){
		/*
		 //为本页面添加分享回调参数
		this.addWxShareCallbackParam("share_type_mark", "45");
		this.addWxShareCallbackParam("media_no", "sina");
		this.setWxShareDesc("这是描述");
		this.setWxShareTitle("这是标题");
		this.setWxShareImage("https://oss.ddd.ddc.c/gg.jpg");
		//修改分享页面的url为http://sam.local.tb21.cn/struts2_h5/activity/demo
		this.setWxShareUrl("http://sam.local.tb21.cn/struts2_h5/activity/demo");
		
		*/
		
		
		return SUCCESS; 
	}

	
	public String demo(){
	
		return SUCCESS; 
	}
	
}

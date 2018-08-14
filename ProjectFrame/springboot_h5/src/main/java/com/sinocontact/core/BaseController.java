package com.sinocontact.core;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.sinocontact.core.service.WxAuthService;
import com.sinocontact.utils.CookieUtils;

/**
 * 所有controller的基类
 * @author sam
 * @since 2018年5月15日
 */
@Controller  
public class BaseController {

	private static final Logger logger = Logger.getLogger(BaseController.class);
		
	//公用
	private HttpServletRequest request ;
	private HttpServletResponse response;	
	
	
	public HttpServletRequest getRequest() {
		return request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	
	
	@ModelAttribute
	public void setRequestAndResponse(HttpServletRequest request,HttpServletResponse response){		
		this.request = request;
		this.response = response;		
	}	
	
	/**
     * 得到get/post上来的request中的参数名对应的值
     * @param key 参数名称
     * @return String  参数值
     * @author sam
     * @since 2018年5月21日
     */
    public String getParam(String key){
		String param = getRequest().getParameter(key);
		return param;
		
	}
	/**
	 * 添加页面变量属性
	 * @param name
	 * @param obj void 
	 * @author sam
	 * @since 2018年5月15日
	 */
	public void putObject(String name,Object obj){
		//model.addAttribute(name, obj);
		request.setAttribute(name, obj); 
	}
	/**
     * 向cookie中写入一条记录
     * @param name
     * @param value void 
     * @author sam
     * @since 2018年4月11日
     */
    public void setCookieValue(String name,String value){
    	CookieUtils.setCookieValue(response, name, value);    	
    }
    /**
     * 
     * @param name
     * @param value
     * @param expiry 失效时间（秒） 
     * @author sam
     * @since 2018年5月15日
     */
    public void  setCookieValue(String name,String value,int expiry){
    	CookieUtils.setCookieValue(response, name, value, expiry);    	
    }
    /**
     * 删除指定的cookie
     * @param name void 
     * @author sam
     * @since 2018年5月15日
     */
    public void deleteCookie(String name){
    	CookieUtils.deleteCookie(response, name);    	
    }
    
    /**
     * 从cookie中获取key为name的值
     * @param name
     * @return String 
     * @author sam
     * @since 2018年4月11日
     */
    public  String getCookieValue(String name){
    	return CookieUtils.getCookieValue(request, name);
		
	}
    /**
     * 返回用户进行微信分享时，分享的url地址
     * @return String 
     * @author sam
     * @since 2018年5月31日
     */
    public String getWxShareUrl(){
    	
    	Object obj = getRequest().getAttribute("wxShareUrl");
    	if(obj == null){
    		return getRequest().getRequestURL().toString();
    	}
    	String wxShareUrl = obj.toString();    	
    	//如果为空，则返回当前页面的url地址
    	if( StringUtils.isEmpty(wxShareUrl) == true){
    		return getRequest().getRequestURL().toString();
    	}
    	
    	//否则返回指定的url地址
    	return wxShareUrl;
    }
    
    /**
     * 微信分享的显示标题,默认标题为空
     * @return String 
     * @author sam
     * @since 2018年6月1日
     */
    public String getWxShareTitle(){
    	Object obj = getRequest().getAttribute("wxShareTitle");
    	if(obj == null){
    		return "";
    	}
    	String wxShareTitle = obj.toString();    	
    	//如果为空，则返回当前页面的url地址
    	if( StringUtils.isEmpty(wxShareTitle) == true){
    		return "";
    	}
    	
    	//否则返回指定的url地址
    	return wxShareTitle;
    }
    
    /**
     * 得到微信分享的要显示给客户看的描述
     * @return String 
     * @author sam
     * @since 2018年6月1日
     */
    public String getWxShareDesc(){
    	Object obj = getRequest().getAttribute("wxShareDesc");
    	if(obj == null){
    		return "";
    	}
    	String wxShareDesc = obj.toString();    	
    	//如果为空，则返回当前页面的url地址
    	if( StringUtils.isEmpty(wxShareDesc) == true){
    		return "";
    	}
    	
    	//否则返回指定的url地址
    	return wxShareDesc;
    }
    
    /**
     * 微信分享后要显示的图片
     * @return String 
     * @author sam
     * @since 2018年6月1日
     */
    public String getWxShareImage(){
    	Object obj = getRequest().getAttribute("wxShareImage");
    	if(obj == null){
    		return "";
    	}
    	String wxShareImage = obj.toString();    	
    	//如果为空，则返回当前页面的url地址
    	if( StringUtils.isEmpty(wxShareImage) == true){
    		return "";
    	}
    	
    	//否则返回指定的url地址
    	return wxShareImage;
    	
    	
    }
    
    /**
	 * 添加一个微信分享后回调时带回的参数及值。参数格式为：name1=value1&name2=value2&,,,
	 * 如果设置了参数，那么可以在onShareCallback()回调函数中通过
	 * this.getParams(paramName)获得参数值。
	 * @param paramName
	 * @param paramValue void 
	 * @author sam
	 * @since 2018年5月31日
	 */
	public void addWxShareCallbackParam(String paramName,String paramValue){
		Object obj = this.getRequest().getAttribute("onShareCallbackParam");		
		String tempParams = null;
		if(obj!=null){
			tempParams = obj.toString();
		}
		if(StringUtils.isEmpty(tempParams) == true){
			tempParams  = String.format("%s=%s", paramName,paramValue);
		}else{
			tempParams = String.format("%s&%s=%s",tempParams, paramName,paramValue);
		}		
		this.putObject("onShareCallbackParam", tempParams);
	}
	//得到微信分享回调参数
	private String getWxShareCallbackParam(){
		Object obj = this.getRequest().getAttribute("onShareCallbackParam");	
		if(obj == null){
			return "";			
		}
		return obj.toString();
	}
	
	/**
	 * 设置需要分享的微信指定url地址。
	 * 正常情况下，不需要设置，只有在需要分享指定
	 * url地址，并且此url地址不是当前页面的url
	 * 地址时才需要进行手工设置微信分享的url地址 
	 * @param wxShareUrl  指定的url地址
	 * @author sam
	 * @since 2018年5月31日
	 */
	public void setWxShareUrl(String wxShareUrl) {
		
		//设置
		putObject("wxShareUrl", wxShareUrl);
		
		//一旦设置了微信分享url，则需要重新设置微信基本信息。
		//setWxBaseMessage();
	}
	
	/**
	 * 设置需要分享的微信显示标题
	 * @param wxShareTitle  
	 * @author sam
	 * @since 2018年6月1日
	 */
	public void setWxShareTitle(String wxShareTitle){
		//设置
		putObject("wxShareTitle", wxShareTitle);
				
		//一旦设置了微信分享url，则需要重新设置微信基本信息。
		//setWxBaseMessage();
	}
	/**
	 * 微信分享的显示的描述
	 * @param wxShareDesc  
	 * @author sam
	 * @since 2018年6月1日
	 */
	public void setWxShareDesc(String wxShareDesc){
		//设置
		putObject("wxShareDesc", wxShareDesc);
						
		//一旦设置了微信分享url，则需要重新设置微信基本信息。
		//setWxBaseMessage();
	}
	/**
	 * 微信分享后要显示的图片
	 * @param wxShareImage void 
	 * @author sam
	 * @since 2018年6月1日
	 */
	public void setWxShareImage(String wxShareImage){
		//设置
		putObject("wxShareImage", wxShareImage);
								
		//一旦设置了微信分享url，则需要重新设置微信基本信息。
		//setWxBaseMessage();
	}
    //设置每个页面都可能需要的用到的参数信息
  	public void setWxBaseMessage(){
  		
  		//得到微信分享的url地址
  		String wxShareUrl = getWxShareUrl();
  		//得到微信分享的显示标题，描述，图片
  		String wxShareTitle = getWxShareTitle();
  		String wxShareDesc = getWxShareDesc();
  		String wxShareImage = getWxShareImage();
  		
  		//得到ticket token
  		String tiketToken = WxAuthService.getWechatJsApiTicket();  				
  		//时间戳
  		String timestamp = ""+System.currentTimeMillis();  		
  		//随机字符串
  		String noncestr = RandomUtil.generateString(16);  		
  		//appid
  		String appid = WxAuthService.getWeixinProxy().getWeixinAccount().getId();  		
  		//签名
  		String signature = DigestUtil.SHA1("jsapi_ticket="+tiketToken+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+wxShareUrl);
  		
  		//设置微信分享需要的公众号的appid
  		putObject("appid", appid);
  		//设置微信分享需要使用的签名signature
  		putObject("signature", signature);
  		//设置微信分享需要使用的时间戳
  		putObject("timestamp", timestamp);
  		//设置微信分享需要使用的随机字符串
  		putObject("noncestr", noncestr);		
  		//设置微信分享的url，实际上就是当前action的url地址
  		putObject("wxShareUrl", wxShareUrl);  		
  		//设置微信分享的显示标题，描述，图片
  		putObject("wxShareTitle", wxShareTitle);
  		putObject("wxShareDesc", wxShareDesc);
  		putObject("wxShareImage", wxShareImage);
  		
  		//用户分享后回调的参数默认值为空
  		putObject("onShareCallbackParam",getWxShareCallbackParam());
  		
  	}
}

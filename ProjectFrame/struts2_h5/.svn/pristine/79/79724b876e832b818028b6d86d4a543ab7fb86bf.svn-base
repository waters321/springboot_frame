package com.sinocontact.core.service;



import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.foxinmy.weixin4j.cache.FileCacheStorager;
import com.foxinmy.weixin4j.cache.RedisCacheStorager;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.mp.WeixinProxy;
import com.foxinmy.weixin4j.mp.model.OauthToken;
import com.foxinmy.weixin4j.mp.model.User;
import com.foxinmy.weixin4j.type.TicketType;
import com.sinocontact.core.BaseService;
import com.sinocontact.utils.HttpUtils;
import com.sinocontact.utils.PropertyUtils;
import com.sinocontact.utils.wx.UserApi;

/**
 * 处理微信授权的业务类
 * @author sam
 * @since 2018年5月24日
 */
public class WxAuthService extends BaseService {
	private static final Logger logger = Logger.getLogger(WxAuthService.class);
	
	//微信操作代理,所有和微信相关的操作功能集合(静态全局)
	private static WeixinProxy proxy = null;
	
	//微信的access token及对应的失效时间(相当于System.currentTimeMillis()毫秒数)
	private static String wxAccessToken = null;
	private static Long wxAccessTokenExpiry = 0l;
	
	private static String wxTicketToken = null;
	private static Long wxTicketTokenExpiry = 0l;
	
	
	/**
	 * 得到微信操作代理类
	 * @return WeixinProxy 
	 * @author sam
	 * @since 2018年5月24日
	 */
	public static WeixinProxy getWeixinProxy(){
		if(proxy!=null){
			return proxy;
		}
		
		//从配置文件中加载appId和对应的appSecret
		String appId = PropertyUtils.getProperty("wechat.appId");
		String appSecret = PropertyUtils.getProperty("wechat.appSecret");		
		
		//默认把access_token保存在本地的文件中
		proxy = new WeixinProxy(new WeixinAccount(appId, appSecret), new FileCacheStorager<Token>());		
		return proxy;
	}
	/**
	 * 得到微信授权后的微信用户对象
	 * @param code
	 * @return User 
	 * @author sam
	 * @since 2018年5月24日
	 */
	public static User getWeixinUser(String code){
		
		User user = null;
		try{
			OauthToken token = getWeixinProxy().getOauthApi().getAuthorizationToken(code);				
			//判断是否是静默授权
			if(isSilentAuth() == true){//表示静默授权				
				user = new User();				
				user.setOpenId(token.getOpenId());	//只有openid		
			}else{//正常授权				
				user = getWeixinProxy().getOauthApi().getAuthorizationUser(token);
			}			
		}catch(Exception e){
			user = null;
			logger.error("",e);
		}
        return user;
	}
	
	/**
	 * 根据openid获取微信用户对象,如果没有获取到，则返回null
	 * @param openId 
	 * @return User 获取到的微信用户对象
	 * @author sam
	 * @since 2018年5月31日
	 */
	public static User getWeixinUserByOpenId(String openId){
		User user = null;
		try{
			UserApi userApi = new UserApi(getWechatAccessToken());
			user = userApi.getUser(openId);
			userApi = null;
		}catch(Exception e){
			logger.error("",e);
		}
		return user;
	}
	
	
	
	/**
	 * 获取微信公众号的ACCESS_TOKEN
	 * 获取access token有两种方式(实时获取会导致上次的access token失效，一般不推荐):
	 * 第一种是使用时，直接实时从微信官方获取通过官方api获取到
	 * 第二种是由专门的获取access token的线程定时到微信官方那边获取后，保存在redis中，在
	 * 快要失效时，再从微信官方获取新的access token保存到redis中，然后再通过对外提供一个获取access_token的http协议接口;
	 * 我们需要用时，直接通过http协议的get方式，获取到此access_token即可。
	 * @return String 
	 * @author sam
	 * @since 2018年5月25日
	 */
	public static String getWechatAccessToken(){
		
		String accessToken = null;
		try{
			if(isHttpAccessToken() == true){
				accessToken = getAccessTokenFromHttp();
			}else{
				//默认从微信官方获取access_token,（本WeixinProxy内部已经做了本地access_token缓存，则由创建WeixinProxy时中参数确定）
				accessToken = getWeixinProxy().getTokenManager().getAccessToken();
			}						
						
		}catch(Exception e){
			accessToken = null;
			logger.error("",e);
		}		
		return accessToken;
	}
	
	/**
	 * 获取微信公众号的ticket
	 * 获取ticket有两种方式(实时获取会导致上次的ticket失效，一般不推荐):
	 * 第一种是使用时，直接实时从微信官方获取通过官方api获取到
	 * 第二种是由专门的获取ticket的线程定时到微信官方那边获取后，保存在redis中，在
	 * 快要失效时，再从微信官方获取新的ticket保存到redis中，然后再通过对外提供一个获取ticket的http协议接口;
	 * 我们需要用时，直接通过http协议的get方式，获取到此ticket即可。
	 * @return String 
	 * @author sam
	 * @since 2018年5月30日
	 */
	public static String getWechatJsApiTicket(){
		String ticket = null;
		try{
			if(isHttpAccessToken() == true){
				ticket = getJsApiTicketFromHttp();
			}else{
				//默认从微信官方获取ticket,（本WeixinProxy内部已经做了本地ticket缓存，则由创建WeixinProxy时中参数确定）				
				ticket = getWeixinProxy().getTicketManager(TicketType.jsapi).getAccessToken();
			}	
		}catch(Exception e){
			ticket = null;
			logger.error("",e);
		}
		return ticket;		
		
	}
	/**
	 * 从http协议配置好的地址获取ticket
	 * @return String 获取到的ticket,如果没有获取到，则返回null
	 * @author sam
	 * @since 2018年5月30日
	 */
	private static String getJsApiTicketFromHttp(){
		
		//先确认缓存中的wxTicketToken是否已经失效，如果没有失效，则直接返回缓存中的wxTicketToken
		if( (System.currentTimeMillis()<wxTicketTokenExpiry) && (StringUtils.isEmpty(wxTicketToken) == false) ){
			return wxTicketToken;
		}
		
		//从http协议那边获取wxTicketToken
		String ticket =  HttpUtils.sendGet(PropertyUtils.getProperty("wechat.http.ticket.url"));
		if(StringUtils.isEmpty(ticket) ==true){
			return null;
		}
		
		//把最新获取的到ticketToken保存到缓存中，并重新设置失效时间（60*1000l 表示1分钟）
		wxTicketToken = ticket;
		wxTicketTokenExpiry = System.currentTimeMillis()+60*1000l;
		
		return ticket;
	}
	/**
	 * 从http协议配置好的地址获取access_token
	 * @return String 获取到的access_token,如果没有获取到，则返回null
	 * @author sam
	 * @since 2018年5月30日
	 */
	private static String getAccessTokenFromHttp(){
		
		// 先确认缓存中的wxAccessToken是否已经失效，如果没有失效，则直接返回缓存中的wxAccessToken
		if ((System.currentTimeMillis() < wxAccessTokenExpiry) && (StringUtils.isEmpty(wxAccessToken) == false)) {
			return wxAccessToken;
		}
				
		//从http协议那边获取wxAccessToken
		String accessToken = HttpUtils.sendGet(PropertyUtils.getProperty("wechat.http.accesstoken.url"));
		if(StringUtils.isEmpty(accessToken) ==true){
			return null;
		}
		
		// 把最新获取的到wxAccessToken保存到缓存中，并重新设置失效时间（60*1000l 表示1分钟）
		wxAccessToken = accessToken;
		wxAccessTokenExpiry = System.currentTimeMillis() + 60 * 1000l;
		return accessToken;
	}
	
	/**
	 * 生成真正的微信回调url
	 * @param request
	 * @param redirectUrl 需要返回到之前的页面url 
	 * @return String 
	 * @author sam
	 * @since 2018年5月24日
	 */
	public static String createWechatCallbackUrl(HttpServletRequest request,String redirectUrl){
		
		//得到配置文件中的微信授权后需要回调的url地址
		String wechatCallbackUrl = PropertyUtils.getProperty("wechat.auth.callback");
		
		String realCallBackUrl = "";    	
    	if(StringUtils.isEmpty(wechatCallbackUrl) == true){//说明配置文件中没有配置微信回调地址
    		//组装出微信回调地址    		    		
    		String hostPath = request.getScheme()+"://"+request.getServerName();
    		if(request.getServerPort()!=80){
    			hostPath=hostPath+":"+request.getServerPort();
    		}
    		if(StringUtils.isEmpty(request.getContextPath()) == false){//如果有项目路径，则添加上项目路径
    			hostPath = hostPath+request.getContextPath();
    		}
    		realCallBackUrl = String.format("%s/wechatCallback?redirectUrl=%s", hostPath,redirectUrl);    		
    		
    	}else{
    		//使用配置文件中的配置
    		realCallBackUrl = String.format(wechatCallbackUrl, redirectUrl);
    	}  
    	return realCallBackUrl;
	}
	
	/**
	 * 是否是从redis中获取access_token
	 * @return boolean true表示从redis获取，false表示实时获取或从自己本地的缓存中获取
	 * @author sam
	 * @since 2018年5月25日
	 */
	public static boolean isHttpAccessToken(){
		// 从配置文件中获取配置的access_token获取方式
		String httpBase = PropertyUtils.getProperty("wechat.http.accesstoken");
		if (StringUtils.isEmpty(httpBase) == false) {// 没有配置获取方式的话，默认就是实时获取或从自己本地的缓存中获取。
			if ("true".equals(httpBase.toLowerCase()) == true) {// 表示从http获取
				return true;
			}
		}

		return false;
	}
	
	/**
	 * 是否是静默授权
	 * @return boolean true表示是静默授权，false表示不是 
	 * @author sam
	 * @since 2018年5月24日
	 */
	public static boolean isSilentAuth(){
		// 从配置文件中获取配置的授权方式
		String authBase = PropertyUtils.getProperty("wechat.auth.base");
		if (StringUtils.isEmpty(authBase) == false) {// 没有配置授权方式的话，默认就是正常授权。
			if ("true".equals(authBase.toLowerCase()) == true) {// 表示静默授权
				return true;
			}
		}
		
		return false;
	}
	/**
	 * 生成微信授权url
	 * @param realCallBackUrl
	 * @return String 
	 * @author sam
	 * @since 2018年5月24日
	 */
	public static String createWxAuthUrl(String realCallBackUrl){
		
		//判断是否是静默授权
		if(isSilentAuth() == true){//表示静默授权
			return getWeixinProxy().getOauthApi().getUserAuthorizationURL(realCallBackUrl, "STATE", "snsapi_base");			
		}else{		
			//正常的授权方式
			return getWeixinProxy().getOauthApi().getUserAuthorizationURL(realCallBackUrl, "STATE", "snsapi_userinfo");
		}
	}
	
}

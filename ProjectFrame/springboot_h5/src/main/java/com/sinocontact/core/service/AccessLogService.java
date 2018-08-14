package com.sinocontact.core.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sinocontact.common.ConstCommon;
import com.sinocontact.core.BaseService;
import com.sinocontact.core.dao.AccessLogDao;
import com.sinocontact.entity.AccessLog;
import com.sinocontact.utils.CookieUtils;

/**
 * 访问日志业务
 * @author sam
 * @since 2018年5月17日
 */
public class AccessLogService extends BaseService {

	private static final Logger logger = Logger.getLogger(AccessLogService.class);
	
	//内存全局类
	private static AccessLogDao accessLogDao  = new AccessLogDao();
	
	/**
	 * 向数据库中插入一条访问日志
	 * @param accessLog  
	 * @author sam
	 * @since 2018年5月17日
	 */
	public static void addAccessLog(AccessLog accessLog){
		accessLogDao.addAccessLog(accessLog);
	}
	
	/**
	 * 向数据库中插入一条访问日志
	 * @param ip_address
	 * @param user_agent
	 * @param media_no
	 * @param openid
	 * @param share_openid
	 * @param access_url void 
	 * @author sam
	 * @since 2018年5月17日
	 */
	public static void addAccessLog(String ip_address,String user_agent,String media_no,String openid,String share_openid,String access_url){
		AccessLog accessLog = new AccessLog();
		accessLog.setAccess_url(access_url);
		accessLog.setIp_address(ip_address);
		accessLog.setMedia_no(media_no);
		accessLog.setOpenid(share_openid);
		accessLog.setShare_openid(share_openid);
		accessLog.setUser_agent(user_agent);
		accessLogDao.addAccessLog(accessLog);
	}
	/**
	 * 向数据库中插入一条访问日志
	 * @param request void 
	 * @author sam
	 * @since 2018年5月17日
	 */
	public static void addAccessLog(HttpServletRequest request,HttpServletResponse response){
		
		String access_url = request.getRequestURI();
		String user_agent = request.getHeader("User-Agent");
		String ip_address = getIpAddress(request);
		String media_no = handleMediaNo(request,response);
		String share_openid = handleShareOpenId(request,response);
		String openid = CookieUtils.getCookieValue(request, ConstCommon.OPEN_ID);
		
		addAccessLog( ip_address, user_agent, media_no, openid, share_openid, access_url);
	}
	
	/**
	 * 从url或post的数据中提取media_no的值，保存到cookie中
	 * @param invocation
	 * @return String 
	 * @author sam
	 * @since 2018年4月11日
	 */
	private static String handleMediaNo(HttpServletRequest request,HttpServletResponse response){
		
		//从url或post的数据中提取media_no的值，保存到cookie中
		String media_no = request.getParameter("media_no");		
		if (media_no != null) {
			CookieUtils.setCookieValue(response,"media_no", media_no,ConstCommon.COOKIE_EXPIRY);			
		}else{
			//从cookie中取出返回给写日志的地方使用
			media_no = CookieUtils.getCookieValue(request,"media_no");
		}
		return media_no;
	}
	
	/**
	 * 从url或post的数据中提取share_openid的值，保存到cookie中
	 * @param invocation
	 * @return String
	 * @author sam
	 * @since 2018年4月11日
	 */
	private static String handleShareOpenId(HttpServletRequest request,HttpServletResponse response){
		
		//从url或post的数据中提取share_openid的值，保存到cookie中
		String share_openid = request.getParameter("share_openid");		
		if (share_openid != null) {
			CookieUtils.setCookieValue(response,"share_openid", share_openid);			
		}else{
			//从cookie中取出返回给写日志的地方使用
			share_openid = CookieUtils.getCookieValue(request,"share_openid");
		}
		return share_openid;
	}
	
	/**
	 * 得到ip地址
	 * @param request
	 * @return String 
	 * @author sam
	 * @since 2018年4月11日
	 */
	private static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}

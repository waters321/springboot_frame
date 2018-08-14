package com.sinocontact.entity;

import java.util.Date;

/**
 * 访问日志bean
 * @author sam
 * @since 2018年5月17日
 */
public class AccessLog {

	private String ip_address; //访问者的ip地址	
	private String user_agent; //访问者浏览器的useragent
	private String media_no; //推广媒体的标记
	private String openid; //微信的openid
	private String share_openid; //分享者的微信openid
	private String access_url; //访问的页面地址
	private Date create_time; //create_time
	
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	public String getUser_agent() {
		return user_agent;
	}
	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}
	public String getMedia_no() {
		return media_no;
	}
	public void setMedia_no(String media_no) {
		this.media_no = media_no;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getShare_openid() {
		return share_openid;
	}
	public void setShare_openid(String share_openid) {
		this.share_openid = share_openid;
	}
	public String getAccess_url() {
		return access_url;
	}
	public void setAccess_url(String access_url) {
		this.access_url = access_url;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
}

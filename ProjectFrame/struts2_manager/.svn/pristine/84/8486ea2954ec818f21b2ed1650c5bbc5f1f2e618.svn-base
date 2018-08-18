package com.sinocontact.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回的数据 
 * 说明：附带的数据放在map中，可以根据需要添加多个值，
 * 添加方式为 通过putData(key,value)函数实现
 * @author sam
 * @since 2018年5月22日
 */
public class ResultData {

	//0：表示正常,其他：表示异常
	private Integer code=0;
	//返回的信息
	private String msg="";	
	//附带数据
	private Map<String,Object> data = new HashMap<String,Object>();
	
	/**
	 * 默认构造函数
	 */
	public ResultData(){
		setResultData(code,msg,null);
	}
	/**
	 * 直接设定数据的构造函数
	 * @param code	错误码
	 * @param msg	提示信息
	 * @param access_token data中的数据
	 */
	public ResultData(Integer code,String msg,String access_token){
		setResultData(code,msg,access_token);
	}	
	/**
	 * 直接设定数据的构造函数
	 * @param code	错误码
	 * @param msg	提示信息
	 * @param access_token data中的数据 
	 * @author sam
	 * @since 2018年5月23日
	 */
	public void setResultData(Integer code,String msg,String access_token){
		setCode(code);
		setMsg(msg);
		putData("access_token", access_token);
	}
	/**
	 * 添加附带数据的方法
	 * @param key  要添加的数据的字段名
	 * @param value  要添加的数据的值
	 * @author sam
	 * @since 2018年5月23日
	 */
	public void putData(String key,Object value){
		data.put(key, value);
	}
	
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<String,Object> getData() {
		return data;
	}
	public void setData(Map<String,Object> data) {
		this.data = data;
	}	
	public class Data{
		private String access_token;
	}
}

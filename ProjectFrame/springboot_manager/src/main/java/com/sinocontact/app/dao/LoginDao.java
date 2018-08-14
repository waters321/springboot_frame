package com.sinocontact.app.dao;

import org.apache.log4j.Logger;

public class LoginDao extends BaseDao {
	private static final Logger logger = Logger.getLogger(LoginDao.class);
	
	/**
	 * 判断用户是否有效
	 * @param username
	 * @param password
	 * @return boolean 
	 * @author sam
	 * @since 2018年5月14日
	 */
	public boolean isValidUser(String username,String password){
		try{
			Long cnt = this.getMainDbOperator().queryLong("select count(*) from a_user where status=0 and username=? and password=?",username,password);
			if(cnt.longValue()>0){
				return true;
			}
		}catch(Exception e){
			logger.error("",e);
		}
		return false;
		
	}
}

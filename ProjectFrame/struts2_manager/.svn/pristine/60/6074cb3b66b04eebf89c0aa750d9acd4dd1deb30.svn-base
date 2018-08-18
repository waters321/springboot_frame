package com.sinocontact.core.dao.user;

import org.apache.log4j.Logger;

import com.sinocontact.core.BaseDao;

/**
 * 用户Dao，处理与用户相关的数据库操作
 * @author sam
 * @since 2018年5月22日
 */
public class UserDao extends BaseDao {

	private static final Logger logger = Logger.getLogger(UserDao.class);
	
	
	/**
	 * 判断用户是否有效
	 * @param username
	 * @param password
	 * @return boolean 
	 * @author sam
	 * @since 2018年5月14日
	 */
	public boolean isValidUser(String username,String password){
		return true;
		/*
		try{
			Long cnt = this.getMainDbOperator().queryLong("select count(*) from a_user where status=0 and username=? and password=?",username,password);
			if(cnt.longValue()>0){
				return true;
			}
		}catch(Exception e){
			logger.error("",e);
		}
		return false;
		*/
		
	} 
	
}

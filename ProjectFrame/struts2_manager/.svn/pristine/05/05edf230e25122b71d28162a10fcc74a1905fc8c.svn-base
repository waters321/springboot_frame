package com.sinocontact.core;

import org.apache.log4j.Logger;

import com.sinocontact.dbutils.DBControl;
import com.sinocontact.dbutils.operator.DbOperator;

/**
 * Dao操作的基类，所有的Dao都要继承此基类
 * @author sam
 * @since 2018年5月21日
 */
public class BaseDao {
	
	private static final Logger logger = Logger.getLogger(BaseDao.class);
	
	/**
	 * 得到正常的数据库操作对象
	 * @return DbOperator 
	 * @author sam
	 * @since 2018年5月14日
	 */
	public DbOperator getMainDbOperator(){
		return DBControl.getMainDbOperator();
	}	
	
	/**
	 * 得到事务类型的数据库操作对象
	 * @return DbOperator 
	 * @author sam
	 * @since 2018年5月14日
	 */
	public DbOperator getTransactionMainDbOperator(){
		return DBControl.getTransactionMainDbOperator();
	}
}

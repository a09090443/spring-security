package com.localhost.dao;

import java.util.List;

import com.localhost.base.dao.BaseHibernateDAO;
import com.localhost.model.UserInfo;

public interface IUserInfoDAO extends BaseHibernateDAO<UserInfo> {
	
	public String getMaxUserId();
	
	public List<Object[]> findAllUserDetail();
	
	public List<UserInfo> findAllUserExceptDelete();

	public UserInfo findAliveUserByLoginId(String loginId);
	
	public UserInfo findUserByLoginId(String loginId);
	
	public Object[] findUserByUserId(String userId);


}

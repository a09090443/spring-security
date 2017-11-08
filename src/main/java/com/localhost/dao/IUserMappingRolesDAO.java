package com.localhost.dao;

import com.localhost.base.dao.BaseHibernateDAO;
import com.localhost.model.UserMappingRoles;

public interface IUserMappingRolesDAO extends BaseHibernateDAO<UserMappingRoles> {
	
	public void deleteByloginId(String loginId);
	
}

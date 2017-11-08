package com.localhost.dao;

import java.util.List;

import com.localhost.base.dao.BaseHibernateDAO;
import com.localhost.model.UserRole;

public interface IUserRoleDAO extends BaseHibernateDAO<UserRole> {

	public List<UserRole> findUserMappingRoleTypeByLoginId(String loginId);
	
}

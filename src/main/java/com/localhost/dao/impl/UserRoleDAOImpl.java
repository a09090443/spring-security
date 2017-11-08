package com.localhost.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.localhost.base.dao.impl.BaseHibernateDAOImpl;
import com.localhost.dao.IUserRoleDAO;
import com.localhost.model.UserRole;

@Repository("userRoleDao")
public class UserRoleDAOImpl extends BaseHibernateDAOImpl<UserRole> implements IUserRoleDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserRoleDAOImpl.class);

	public UserRoleDAOImpl() {
		this.setClazz(UserRole.class);
	}

	@Override
	@Cacheable(value = "userRolesCache", key = "#loginId")
	public List<UserRole> findUserMappingRoleTypeByLoginId(String loginId) {
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("loginId", loginId);
		StringBuilder hqlStr = new StringBuilder();
		hqlStr.append("SELECT R ");
		hqlStr.append("FROM UserMappingRoles U, UserRole R ");
		hqlStr.append("WHERE U.pk.roleId = R.roleId AND U.pk.loginId = :loginId ");
		List<UserRole> roleList = (List<UserRole>) this.findByHql(hqlStr.toString(), argMap);
		return CollectionUtils.isEmpty(roleList) ? null : roleList;
	}

}

package com.localhost.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.localhost.base.dao.impl.BaseHibernateDAOImpl;
import com.localhost.dao.IUserMappingRolesDAO;
import com.localhost.model.UserMappingRoles;

@Repository("userMappingRolesDao")
public class UserMappingRolesDAOImpl extends BaseHibernateDAOImpl<UserMappingRoles> implements IUserMappingRolesDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserMappingRolesDAOImpl.class);

	public UserMappingRolesDAOImpl() {
		this.setClazz(UserMappingRoles.class);
	}

	@Override
	public void deleteByloginId(String loginId) {
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("loginId", loginId);
		String hqlStr = "DELETE FROM UserMappingRoles U WHERE U.pk.loginId=:loginId";
		this.executeHql(hqlStr, argMap);
	}

}

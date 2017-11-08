package com.localhost.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.localhost.base.dao.impl.BaseHibernateDAOImpl;
import com.localhost.dao.IUserInfoDAO;
import com.localhost.model.CurrentStatus;
import com.localhost.model.UserInfo;

@Repository("userInfoDao")
public class UserInfoDAOImpl extends BaseHibernateDAOImpl<UserInfo> implements IUserInfoDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserInfoDAOImpl.class);

	public UserInfoDAOImpl() {
		this.setClazz(UserInfo.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getMaxUserId() {
		String hqlStr = "FROM UserInfo U ORDER BY U.userId DESC";
		List<UserInfo> userInfoList = (List<UserInfo>) this.findByHql(hqlStr, null);
		return CollectionUtils.isEmpty(userInfoList) ? "0" : userInfoList.get(0).getUserId();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findAllUserDetail() {

		StringBuilder hqlStr = new StringBuilder();
		// Get columns result
		hqlStr.append("SELECT U.userId, U.loginId, U.firstName, U.lastName, U.statusId, U.email, ");
		hqlStr.append("U.image, U.birthday, U.address, U.phone, U.registerTime, U.titleId, C.statusName, P.titleName ");
		// From tables
		hqlStr.append("FROM UserInfo U, UserMappingRoles M, CurrentStatus C, PersonalTitle P ");
		// Select conditions
		hqlStr.append("WHERE U.statusId = C.statusId ");
		hqlStr.append("AND U.titleId = P.titleId GROUP BY U.userId");
		List<Object[]> userList = (List<Object[]>) this.findByHql(hqlStr.toString(), null);

		return CollectionUtils.isEmpty(userList) ? null : userList;
	}

	@Override
	public Object[] findUserByUserId(String userId) {
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("userId", userId);

		StringBuilder hqlStr = new StringBuilder();
		hqlStr.append("FROM UserInfo U, UserRule R ");
		hqlStr.append("WHERE U.ruleId = R.ruleId AND U.userId = :userId ");
		List<?> userList = this.findByHql(hqlStr.toString(), argMap);
		return CollectionUtils.isEmpty(userList) ? null : (Object[]) userList.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	// @Cacheable(value = "userCache", key = "#loginId")
	public UserInfo findAliveUserByLoginId(String loginId) {
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("loginId", loginId);
		argMap.put("statusId", CurrentStatus.DELETE);

		StringBuilder hqlStr = new StringBuilder();
		// From tables
		hqlStr.append("FROM UserInfo U ");
		// Select conditions
		hqlStr.append("WHERE U.statusId != :statusId AND U.loginId = :loginId ");
		List<UserInfo> userList = (List<UserInfo>) this.findByHql(hqlStr.toString(), argMap);
		return CollectionUtils.isEmpty(userList) ? null : userList.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserInfo> findAllUserExceptDelete() {
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("statusId", CurrentStatus.DELETE);

		StringBuilder hqlStr = new StringBuilder();
		// From tables
		hqlStr.append("FROM UserInfo U ");
		// Select conditions
		hqlStr.append("WHERE U.statusId != :statusId ");
		List<UserInfo> userList = (List<UserInfo>) this.findByHql(hqlStr.toString(), argMap);
		return CollectionUtils.isEmpty(userList) ? null : userList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserInfo findUserByLoginId(String loginId) {
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("loginId", loginId);
		StringBuilder hqlStr = new StringBuilder();
		// From tables
		hqlStr.append("FROM UserInfo U ");
		// Select conditions
		hqlStr.append("WHERE U.loginId = :loginId ");
		List<UserInfo> userList = (List<UserInfo>) this.findByHql(hqlStr.toString(), argMap);
		return CollectionUtils.isEmpty(userList) ? null : userList.get(0);
	}

}

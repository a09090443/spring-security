package com.localhost.service;

import java.util.List;

import com.localhost.model.UserInfo;
import com.localhost.model.UserRole;

public interface IUserService {
	public List<UserInfo> findAllUser() throws Exception;

	public List<UserRole> findUserRoleByLoginId(String loginId);
	
	public List<Object[]> findAllUserDetail() throws Exception;

	public Object[] getAllInfoElement();

	public Object[] findUserAllInfoByLoginId(String loginId);

	public void addUser(UserInfo userInfo) throws Exception;

	public void addRoleMapping(String loginId, String roles);

	public void updateUser(UserInfo userInfo);

	public void deleteUser(String userId, String loginId) throws Exception;
	
	public void updateUserByConditions(UserInfo userInfo) throws Exception;


}

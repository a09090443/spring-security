package com.localhost.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.localhost.dao.IPersonalTitleDAO;
import com.localhost.dao.IUserInfoDAO;
import com.localhost.dao.IUserMappingRolesDAO;
import com.localhost.dao.IUserRoleDAO;
import com.localhost.model.CurrentStatus;
import com.localhost.model.PersonalTitle;
import com.localhost.model.UserInfo;
import com.localhost.model.UserMappingRoles;
import com.localhost.model.UserMappingRolesPK;
import com.localhost.model.UserRole;
import com.localhost.service.IUserService;
import com.usefulness.utils.date.DateUtils;
import com.usefulness.utils.image.ImageUtils;

@Transactional
@Service("userService")
public class UserServiceImpl implements IUserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private Integer maxUserId;

	@Autowired
	private IUserInfoDAO userInfoDao;

	@Autowired
	private IUserRoleDAO userRoleDao;

	@Autowired
	private IPersonalTitleDAO personalTitleDao;

	@Autowired
	private IUserMappingRolesDAO userMappingRolesDao;

	public List<UserInfo> findAllUser() throws Exception {
		List<Object[]> userList = userInfoDao.findAllUserDetail();
		List<UserInfo> allUsers = new ArrayList<UserInfo>();
		if(null != userList){
			for (Object[] obj : userList) {
				UserInfo userInfo = new UserInfo();
				userInfo.setUserId(obj[0].toString());
				userInfo.setLoginId(obj[1].toString());
				userInfo.setFirstName(obj[2].toString());
				userInfo.setLastName(obj[3].toString());
				userInfo.setStatusId(obj[4].toString());
				userInfo.setEmail(obj[5].toString());
				userInfo.setImage(obj[6].toString());
				userInfo.setBirthday(obj[7].toString());
				userInfo.setAddress(obj[8].toString());
				userInfo.setPhone(obj[9].toString());
				userInfo.setRegisterTime(obj[10].toString());
				userInfo.setTitleId(obj[11].toString());
				userInfo.setStatusName(obj[12].toString());
				allUsers.add(userInfo);
			}
		}
		return allUsers;
	}

	public List<Object[]> findAllUserDetail() throws Exception {
		return userInfoDao.findAllUserDetail();
	}

	@Override
	public Object[] findUserAllInfoByLoginId(String loginId) {
		Object[] objs = null;
		UserInfo userInfo = userInfoDao.findAliveUserByLoginId(loginId);
		if (null != userInfo) {
			List<UserRole> userRoleList = userRoleDao.findUserMappingRoleTypeByLoginId(userInfo.getLoginId());
			objs = new Object[] { userInfo, userRoleList };
		}

		return objs;
	}

	@Override
	public void addUser(UserInfo userInfo) throws Exception {
		UserInfo checkUser = userInfoDao.findUserByLoginId(userInfo.getLoginId());
		if(null != checkUser){
			throw new Exception("This login_id has been registered!!");
		}
		Integer newUserId = 0;
		String getTime;
		if (null == maxUserId) {
			String userId = userInfoDao.getMaxUserId();
			if (userId.equals("0")) {
				maxUserId = 0;
			} else {
				maxUserId = Integer.valueOf(userId);
			}
		}
		newUserId = maxUserId + 1;
		getTime = DateUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss");
		String formatStr = "%06d";
		String newUserIdStr = String.format(formatStr, newUserId);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		userInfo.setUserId(newUserIdStr);
		userInfo.setStatusId(CurrentStatus.NORMAL);
		userInfo.setRegisterTime(getTime);
		userInfo.setImage(newUserIdStr + "." + ImageUtils.IMAGE_TYPE_JPG);
		userInfoDao.save(userInfo);
		maxUserId += 1;
	}

	@Override
	public void updateUser(UserInfo userInfo) {
		userInfoDao.update(userInfo);
	}

	@Override
	public void addRoleMapping(String loginId, String roles) {

		userMappingRolesDao.deleteByloginId(loginId);

		for (String roleId : roles.split(",")) {
			UserMappingRolesPK pk = new UserMappingRolesPK();
			UserMappingRoles userMappingRoles = new UserMappingRoles();
			pk.setLoginId(loginId);
			pk.setRoleId(roleId);
			userMappingRoles.setPk(pk);
			userMappingRolesDao.save(userMappingRoles);
		}
	}

	@Override
	public List<UserRole> findUserRoleByLoginId(String loginId) {
		List<UserRole> userMappingRoleTypeList = userRoleDao.findUserMappingRoleTypeByLoginId(loginId);
		return userMappingRoleTypeList;
	}

	@Override
	public void deleteUser(String userId, String loginId) throws Exception {
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("userId", userId);
		argMap.put("loginId", loginId);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userId);
		userInfo.setLoginId(loginId);
		userInfo.setStatusId(CurrentStatus.DELETE);
		userInfoDao.update(userInfo, argMap);
	}

	@Override
	public void updateUserByConditions(UserInfo userInfo) throws Exception {
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("userId", userInfo.getUserId());
		argMap.put("loginId", userInfo.getLoginId());
		userInfoDao.update(userInfo, argMap);
	}

	@Override
	public Object[] getAllInfoElement() {
		List<UserRole> userRoleList = userRoleDao.findAll();
		List<PersonalTitle> personalTitleList = personalTitleDao.findAll();
		Object[] elements = new Object[] { userRoleList, personalTitleList };
		return elements;
	}

}

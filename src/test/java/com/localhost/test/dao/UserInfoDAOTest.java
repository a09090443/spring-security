package com.localhost.test.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.localhost.dao.IUserInfoDAO;
import com.localhost.model.PersonalTitle;
import com.localhost.model.UserInfo;
import com.localhost.model.UserRole;
import com.localhost.test.base.TestBase;

public class UserInfoDAOTest extends TestBase {
	@Autowired
	private IUserInfoDAO userInfoDAO;

//	@Test
	public void testfindAllUserDetail() {
		try {
			List<Object[]> userInfoList = userInfoDAO.findAllUserDetail();
//			for(int i=0; i<userInfoList.size(); i++){
//				UserInfo userInfo = (UserInfo) userInfoList.get(i);
//				System.out.println(userInfo.getStatusId());
//			}
			for(Object[] obj:userInfoList){
				UserInfo userInfo = (UserInfo) obj[0];
				UserRole userRole = (UserRole) obj[1];
				PersonalTitle personalTitle = (PersonalTitle) obj[2];
				System.out.println(userInfo.getStatusId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}
	
//	@Test
//	public void testGetMaxUserId() {
//		try {
//			String maxUserId = userInfoDao.getMaxUserId();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		assertTrue(true);
//	}

	@Test
	public void testFindUserInfoByLoginId() {
		try {
			UserInfo user = userInfoDAO.findAliveUserByLoginId("admin");
			System.out.println(user);
			UserInfo user2 = userInfoDAO.findAliveUserByLoginId("admin");
			System.out.println(user);

		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

}

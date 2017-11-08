package com.localhost.test.dao;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.localhost.dao.IUserRoleDAO;
import com.localhost.model.UserRole;
import com.localhost.test.base.TestBase;

public class UserRoleDAOTest extends TestBase {
	@Autowired
	private IUserRoleDAO userRoleDAO;

//	@Test
	public void testAddUserRule() {
		try {
			List<UserRole> userRoleList = new ArrayList<UserRole>();
			userRoleList.add(new UserRole("1", "ADMIN"));
			userRoleList.add(new UserRole("2", "USER"));
			for (UserRole userRole : userRoleList) {
				userRoleDAO.save(userRole);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

	@Test
	public void testFindUserMappingRoleTypeByloginId() {
		try {
			List<UserRole> test = userRoleDAO.findUserMappingRoleTypeByLoginId("admin");

			System.out.println(test);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(true);
	}

}

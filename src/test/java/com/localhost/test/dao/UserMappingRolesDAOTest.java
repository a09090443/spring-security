package com.localhost.test.dao;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.localhost.dao.IUserMappingRolesDAO;
import com.localhost.test.base.TestBase;

public class UserMappingRolesDAOTest extends TestBase {
	@Autowired
	private IUserMappingRolesDAO userMappingRolesDAO;

//	@Test
	public void testDeleteByLoginId(){
		userMappingRolesDAO.deleteByloginId("test01");
	}
}

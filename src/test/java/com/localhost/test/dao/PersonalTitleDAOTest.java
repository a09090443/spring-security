package com.localhost.test.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.localhost.dao.IPersonalTitleDAO;
import com.localhost.model.PersonalTitle;
import com.localhost.test.base.TestBase;

public class PersonalTitleDAOTest extends TestBase {
	@Autowired
	private IPersonalTitleDAO personalTitleDAO;

	@Test
	public void testFindAll() {
		List<PersonalTitle> personalTitleList = personalTitleDAO.getAll();
		System.out.println(personalTitleList.size());
		List<PersonalTitle> personalTitleList2 = personalTitleDAO.getAll();
		System.out.println(personalTitleList2.size());
		assertTrue(true);
	}

}

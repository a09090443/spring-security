package com.localhost.dao;

import java.util.List;

import com.localhost.base.dao.BaseHibernateDAO;
import com.localhost.model.PersonalTitle;

public interface IPersonalTitleDAO extends BaseHibernateDAO<PersonalTitle> {
	public List<PersonalTitle> getAll();
}

package com.localhost.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.localhost.base.dao.impl.BaseHibernateDAOImpl;
import com.localhost.dao.IPersonalTitleDAO;
import com.localhost.model.PersonalTitle;

@Repository("personalTitleDao")
public class PersonalTitleDAOImpl extends BaseHibernateDAOImpl<PersonalTitle> implements IPersonalTitleDAO {
	private static final Logger logger = LoggerFactory.getLogger(PersonalTitleDAOImpl.class);

	public PersonalTitleDAOImpl() {
		this.setClazz(PersonalTitle.class);
	}

	@Override
	@Cacheable(value = "personalTitleCache")
	public List<PersonalTitle> getAll() {
		StringBuilder hql = new StringBuilder();
		hql.append("FROM PersonalTitle ");
		return (List<PersonalTitle>) this.findByHql(hql.toString(), null);
	}

}

package com.localhost.base.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

public interface BaseHibernateDAO<T> {

	public void setClazz(final Class<T> clazzToSet);

	public T findById(int id);
	
	public List<T> findAll();
	
	public List<T> findAll(int start, int resultSize);
	
	public List<?> findByHql(String hql, Map<String, Object> argMap);

	public void save(final T entity);
	
	public void update(final T entity);
	
	public void update(final T entity, Map<String, Object> argMap) throws Exception;
	
	public void delete(final T entity);
	
	public EntityManager getEntityManager();
	
	public void executeHql(String hql, Map<String, Object> argMap);

}
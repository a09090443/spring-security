package com.localhost.base.dao.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.localhost.base.dao.BaseHibernateDAO;

public abstract class BaseHibernateDAOImpl<T extends Object> implements BaseHibernateDAO<T> {
	private static final Logger logger = LoggerFactory.getLogger(BaseHibernateDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	private Class<T> clazz;
	private String entityName;

	@Override
	public void setClazz(Class<T> clazzToSet) {
		clazz = clazzToSet;
	}

	@Override
	public T findById(int id) {
		return entityManager.find(clazz, id);
	}

	@Override
	public List<T> findAll() {
		return entityManager.createQuery("FROM " + clazz.getSimpleName(), clazz).getResultList();
	}

	@Override
	public List<T> findAll(int start, int resultSize) {
		return entityManager.createQuery("FROM " + clazz.getSimpleName(), clazz).setFirstResult(start)
				.setMaxResults(resultSize).getResultList();
	}

	@Override
	public List<?> findByHql(String hql, Map<String, Object> argMap) {
		Query query = entityManager.createQuery(hql);
		if (null != argMap && argMap.size() > 0) {
			argMap.forEach((k, v) -> {
				logger.info("name:" + k);
				logger.info("value:" + v);
				query.setParameter("ip", "192.168.1.150");
			});
		}
		return query.getResultList();
	}

	@Override
	public void save(final T entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(final T entity) {
		entityManager.merge(entity);
		entityManager.flush();
	}

	@Transactional(readOnly = false)
	public void update(final T entity, Map<String, Object> argMap) throws Exception {
		entityName = entity.getClass().getSimpleName();
		String hqlStr;
		hqlStr = "UPDATE " + entityName + " " + entityName.substring(0, 1) + " SET ";
		hqlStr += this.genHql(entity, argMap, "");
		logger.info("Execute Hql : {}", hqlStr);
		// getCurrentSession().createQuery(hqlStr.toString()).setProperties(argMap).executeUpdate();
		this.executeHql(hqlStr.toString(), argMap);
	}

	@Override
	public void delete(final T entity) {
		T eT = entityManager.merge(entity);
		entityManager.remove(eT);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Transactional(readOnly = false)
	public void executeHql(String hql, Map<String, Object> argMap) {
		Query query = entityManager.createQuery(hql);
		if (null != argMap && argMap.size() > 0) {
			argMap.forEach((k, v) -> {
				logger.info("name:" + k);
				logger.info("value:" + v);
				query.setParameter(k, v);
			});
			query.executeUpdate();
		}
	}

	private String genHql(Object entity, Map<String, Object> argMap, String hqlStr) throws Exception {
		String whereStr = "";
		String entityFirstWord = entityName.substring(0, 1);
		BeanInfo beanInfo = Introspector.getBeanInfo(entity.getClass());
		Integer entityLen = entity.getClass().getSimpleName().length();
		Boolean compareEntityName = entity.getClass().getSimpleName().substring(entityLen - 2, entityLen).equals("PK");
		PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < descriptors.length; i++) {
			String propName = descriptors[i].getName();
			Class<?> propType = descriptors[i].getPropertyType();
			Object value = PropertyUtils.getProperty(entity, propName);
			if ((!propName.equals("class") && null != value) || propName.equals("pk")) {
				if (propType.getSimpleName().equals("Integer") || propType.getSimpleName().equals("int")) {
					if (compareEntityName) {
						hqlStr += entityFirstWord + ".pk." + propName + "=" + value + "";
					} else {
						hqlStr += entityFirstWord + "." + propName + "=" + value + "";
					}
				} else if (propType.getSimpleName().equals("String")) {
					if (compareEntityName) {
						hqlStr += entityFirstWord + ".pk." + propName + "=" + "'" + value + "'";
					} else {
						hqlStr += entityFirstWord + "." + propName + "=" + "'" + value + "'";
					}
				} else if (propType.getSimpleName().equals("Boolean")) {
					hqlStr += entityFirstWord + "." + propName + "=" + "" + value + "";
				} else if (propName.equals("pk")) {
					if (null != value) {
						hqlStr = this.genHql(value, null, hqlStr);
					}
					Class<?> c = null;
					c = Class.forName(propType.getName());
					Field[] fields = c.getDeclaredFields();
					for (Field f : fields) {
						f.setAccessible(true);
					}
					for (Field f : fields) {
						String field = f.toString().substring(f.toString().lastIndexOf(".") + 1);
						if (null != argMap.get(field)) {
							whereStr += entityFirstWord + ".pk." + field + "=:" + field + " AND ";
						}
					}
				} else {
					continue;
				}
				if (null != value) {
					hqlStr += ",";
				}
				if (null != argMap && null != argMap.get(propName)) {
					whereStr += entityFirstWord + "." + propName + "=:" + propName + " AND ";
				}
			}
		}
		if (!whereStr.isEmpty()) {
			whereStr = " WHERE " + whereStr.substring(0, whereStr.length() - 5);
		}
		hqlStr = hqlStr.substring(0, hqlStr.length() - 1) + whereStr;
		return hqlStr;
	}
}

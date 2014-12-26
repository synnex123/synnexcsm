package com.synnex.cms.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
/**
 * @author joeyy
 *function setSessionFactory and getSession
 */
public interface BaseDao{

	public void setSessionFactory(SessionFactory sessionFactory);
	public Session getSession();

}

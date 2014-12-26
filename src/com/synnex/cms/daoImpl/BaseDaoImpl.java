package com.synnex.cms.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.synnex.cms.dao.BaseDao;

public class BaseDaoImpl  implements BaseDao{
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

}

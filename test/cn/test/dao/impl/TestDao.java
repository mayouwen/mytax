package cn.test.dao.impl;

import java.io.Serializable;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.test.dao.ITestDao;
import cn.test.entity.Person;

public class TestDao extends HibernateDaoSupport implements ITestDao {

	public Person findById(Serializable id) {
		return getHibernateTemplate().get(Person.class, id);
	}

	public void save(Person p) {
		getHibernateTemplate().save(p);

	}

}

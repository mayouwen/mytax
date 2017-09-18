package cn.itcast.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.core.util.PageBean;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;

public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
	Class<T> clazz;
	public BaseDaoImpl(){
		ParameterizedType	pt=(ParameterizedType) this.getClass().getGenericSuperclass();//BaseDaoImpl<User>
		clazz=(Class<T>) pt.getActualTypeArguments()[0];
	}
	public void delete(Serializable id) {
		getHibernateTemplate().delete(findById(id));
	}

	public List<T> findAll() {
		Query query = getSession().createQuery("FROM "+clazz.getSimpleName());
		return query.list();
	}
	//条件查询
	public List<T> findAll(String hql,List<Object> parameters){
		Query query = getSession().createQuery(hql);
		if(parameters!=null){
			for(int i=0;i<parameters.size();i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	public List<Info> findAll(QueryHelper queryHelper) {
		Query query = getSession().createQuery(queryHelper.getQueryListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters!=null){
			for(int i=0;i<parameters.size();i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	public PageBean<Info> findAll(QueryHelper queryHelper, int currentPage,
			int pageCount){
		Query query = getSession().createQuery(queryHelper.getQueryListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters!=null){
			for(int i=0;i<parameters.size();i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		if(currentPage<1)currentPage=1; 
		query.setFirstResult((currentPage-1)*pageCount);
		query.setMaxResults(pageCount);
		List<Info> pageData=query.list();
		//总记录数
		Query queryCount = getSession().createQuery(queryHelper.getQueryCount());
		if(parameters!=null){
			for(int i=0;i<parameters.size();i++){
				queryCount.setParameter(i, parameters.get(i));
			}
		}
		long totalCount=(Long) queryCount.uniqueResult();
		return new PageBean<Info>(currentPage, pageCount,totalCount,pageData);
	}
	public T findById(Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}
}

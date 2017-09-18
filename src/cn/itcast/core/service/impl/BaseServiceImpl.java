package cn.itcast.core.service.impl;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.core.service.BaseService;
import cn.itcast.core.util.PageBean;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;

public class BaseServiceImpl<T> implements BaseService<T> {
	private BaseDao<T> basedao;
	public void setBasedao(BaseDao<T> basedao) {
		this.basedao = basedao;
	}
	public void delete(Serializable id) {
		basedao.delete(id);
	}

	public List<T> findAll() {
		return basedao.findAll();
	}

	public T findById(Serializable id) {
		return basedao.findById(id);
	}

	public void save(T entity) {
		basedao.save(entity);
	}

	public void update(T entity) {
		basedao.update(entity);
	}
	public List<T> findAll(String hql, List<Object> parameters) {
		return basedao.findAll(hql, parameters);
	}
	public List<Info> findAll(QueryHelper queryHelper) {
		return basedao.findAll(queryHelper);
	}
	public PageBean<Info> findAll(QueryHelper queryHelper, int currentPage,
			int pageCount){
		return basedao.findAll(queryHelper, currentPage,pageCount);
	}
	    
	
}

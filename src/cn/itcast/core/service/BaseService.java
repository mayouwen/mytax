package cn.itcast.core.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.util.PageBean;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;


public interface BaseService<T> {
	//增加
	void save(T entity);
	//根据id删除
	void delete(Serializable id);
	//更新
	void update(T entity);
	//查找列表
	List<T> findAll(); 
	//根据id查找
	T findById(Serializable id);
	//条件查询
	List<T> findAll(String hql,List<Object> parameters);
	//条件查询
	List<Info> findAll(QueryHelper queryHelper);
	//分页查询
	PageBean<Info> findAll(QueryHelper queryHelper, int currentPage,
			int pageCount);
}

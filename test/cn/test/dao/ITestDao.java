package cn.test.dao;

import java.io.Serializable;

import cn.test.entity.Person;

public interface ITestDao {
	//保存员工
	void save(Person p);
	//按照id查询
	Person findById(Serializable id);
}

package cn.test.service;

import java.io.Serializable;

import cn.test.entity.Person;

public interface ITestService {
	//输出
	void say();
	//保存人员
	void save(Person p);
	//根据id查询
	Person findById(Serializable id);
}

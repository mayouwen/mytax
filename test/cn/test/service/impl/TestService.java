package cn.test.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.test.dao.impl.TestDao;
import cn.test.entity.Person;
import cn.test.service.ITestService;

@Service("testService")
public class TestService implements ITestService {
	@Resource
	TestDao td;
	public void say() {
		System.out.println("测试spring");

	}

	public void save(Person p) {
		td.save(p);
	}

	public Person findById(Serializable id) {
		//td.save(new Person("test"));
		return td.findById(id);
	}

}

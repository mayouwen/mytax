package cn.test;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.test.entity.Person;
import cn.test.service.ITestService;
import cn.test.service.impl.TestService;
public class TestMerge {
	ApplicationContext ac;
	@Before
	public void loadBean() {
		ac= new ClassPathXmlApplicationContext("bean.xml");
	}
	@Test
	public void testSpring() {
		ITestService ts=(ITestService) ac.getBean("testService");
		ts.say();
	}
	@Test
	public void testHibernate(){
		SessionFactory sf= (SessionFactory) ac.getBean("sessionFactory");
		Session session=sf.openSession();
		Transaction transaction = session.beginTransaction();
		//保存
		session.save(new Person("人员4"));
		transaction.commit();
		session.close();
	}
	@Test
	public void testServiceAndDao() {
		ITestService ts=(ITestService) ac.getBean("testService");
		ts.save(new Person("人员5"));
		//System.out.println(ts.findById("297e78ea599590a001599590a3990000").getName());
	}
	@Test
	public void testTransactionReadOnly() {//只读事务，更新操作则回滚
		ITestService ts=(ITestService) ac.getBean("testService");
		System.out.println(ts.findById("297e78ea599590a001599590a3990000").getName());
	}
}
